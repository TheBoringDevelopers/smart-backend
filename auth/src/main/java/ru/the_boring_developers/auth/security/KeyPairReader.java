package ru.the_boring_developers.auth.security;

import javax.net.ssl.SSLContext;
import java.security.KeyPair;

/** Считыватель пары ключей */
public interface KeyPairReader {

    /** Получить SSL контекст */
    SSLContext getSslContext();

    /**
     * Получить пару ключей
     * @return пара ключей
     */
    KeyPair getKeyPair();
}
