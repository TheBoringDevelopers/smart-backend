package ru.the_boring_developers.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import ru.the_boring_developers.auth.entity.IdentifiableUser;
import ru.the_boring_developers.auth.properties.TokenProperties;
import ru.the_boring_developers.auth.security.FileKeyPairReader;
import ru.the_boring_developers.auth.service.user.UserServiceAuth;
import ru.the_boring_developers.common.utils.DateUtil;

import java.io.File;
import java.security.KeyPair;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Конфигурация JWT
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    /** Наименование поля с идентификатором пользователя */
    private static final String USER_ID = "user_id";

    /** Конфигурация безопасности */
    private final TokenProperties tokenProperties;
    /** Сервис работы с данными авторизации */
    private final UserServiceAuth userServiceAuth;

    /**
     * Создает бин jwt-конвертера
     *
     * @return бин jwt-конвертера
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {

            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken token, OAuth2Authentication auth) {
                Long userId = ((IdentifiableUser) auth.getUserAuthentication().getPrincipal()).getId();
                userServiceAuth.updateLoginTime(userId);
                ((DefaultOAuth2AccessToken) token).setAdditionalInformation(Collections.singletonMap(USER_ID, userId));
                return super.enhance(token, auth);
            }

            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
                OAuth2Authentication auth = super.extractAuthentication(map);
                auth.setDetails(map.get(USER_ID));
                return auth;
            }
        };
        KeyPair keyPair = new FileKeyPairReader(
                new File(tokenProperties.getFilePath()),
                tokenProperties.getKeyStorePassword(),
                tokenProperties.getKeyStoreType(),
                tokenProperties.getKeyPassword(),
                tokenProperties.getKeyAlias()
        ).getKeyPair();
        converter.setKeyPair(keyPair);
        return converter;
    }

    /**
     * Создает бин хранилища токенов
     * @return бин хранилища токенов
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter()) {
            @Override
            public OAuth2AccessToken readAccessToken(String tokenValue) {
                OAuth2AccessToken token = super.readAccessToken(tokenValue);
                Long userId = Long.valueOf(String.valueOf(token.getAdditionalInformation().get(USER_ID)));
                OffsetDateTime passwordChanged = userServiceAuth.getPasswordChanged(userId);
                if (passwordChanged != null) {
                    long tokenLifeTime = tokenProperties.getTokenLifeTime();
                    long expiresIn = token.getExpiration().getTime();
                    OffsetDateTime userPasswordChanged = passwordChanged.truncatedTo(ChronoUnit.SECONDS);
                    OffsetDateTime tokenCreated = DateUtil.millisToOffsetDateTime(expiresIn).minusSeconds(tokenLifeTime).truncatedTo(ChronoUnit.SECONDS);
                    if (userPasswordChanged.isAfter(tokenCreated)) {
                        ((DefaultOAuth2AccessToken) token).setExpiration(Date.from(passwordChanged.toInstant()));
                    }
                }
                return token;
            }

            @Override
            public OAuth2RefreshToken readRefreshToken(String tokenValue) {
                final OAuth2RefreshToken refreshToken = super.readRefreshToken(tokenValue);
                final OAuth2Authentication oAuth2Authentication = super.readAuthentication(refreshToken.getValue());
                final String userLogin = (String) oAuth2Authentication.getUserAuthentication().getPrincipal();
                final Long userId = userServiceAuth.getUserIdByLogin(userLogin);
                final OffsetDateTime userPasswordChanged = userServiceAuth.getPasswordChanged(userId).truncatedTo(ChronoUnit.SECONDS);
                long tokenLifeTime = tokenProperties.getRefreshTokenLifeTime();
                long expiresIn = ((DefaultExpiringOAuth2RefreshToken) refreshToken).getExpiration().getTime();
                OffsetDateTime tokenCreated = DateUtil.millisToOffsetDateTime(expiresIn).minusSeconds(tokenLifeTime).truncatedTo(ChronoUnit.SECONDS);
                if (userPasswordChanged.isAfter(tokenCreated)) {
                    throw new BadCredentialsException("Invalid token");
                }
                return refreshToken;
            }
        };
    }
}

