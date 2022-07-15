package ru.the_boring_developers.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import ru.the_boring_developers.auth.properties.TokenProperties;

@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

    /** Конфигурация безопастности */
    private final TokenProperties tokenProperties;

    /** Менеджер аутентификации */
    private final AuthenticationManager authenticationManager;

    /** Хранилище токенов */
    private final TokenStore tokenStore;

    /** Конвертер access-токенов */
    private final AccessTokenConverter accessTokenConverter;

    /** Конвертер access-токенов */
    private final PasswordEncoder passwordEncoder;

    /** Сервис получения {@link org.springframework.security.core.userdetails.UserDetails} */
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        builder
                .withClient("backend-rest-client")
                .secret(passwordEncoder.encode("backend-rest-client"))
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT")
                .scopes("read", "write")
                .accessTokenValiditySeconds(tokenProperties.getTokenLifeTime())
                .refreshTokenValiditySeconds(
                        tokenProperties.getRefreshTokenLifeTime() == 0 ?
                                tokenProperties.getTokenLifeTime() * 3600 :
                                tokenProperties.getRefreshTokenLifeTime()
                );
    }
}
