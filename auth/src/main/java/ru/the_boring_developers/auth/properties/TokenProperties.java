package ru.the_boring_developers.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "auth")
public class TokenProperties {

    /** Время жизни токена */
    private int tokenLifeTime;
    /** Время жизни refreshToken */
    private int refreshTokenLifeTime;
    /** Путь до файла хранилища ключей */
    private String filePath;
    /** Пароль хранилища ключей */
    private String keyStorePassword;
    /** Тип хранилища ключей */
    private String keyStoreType;
    /** Алиас пары ключей */
    private String keyAlias;
    /** Пароль для приватного ключа */
    private String keyPassword;
}
