package ru.the_boring_developers.auth.security;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;

/** Утилитный класс, считывающий пару ключей из хранилища */
@AllArgsConstructor
public class FileKeyPairReader implements KeyPairReader {

    /** Файл хранилища ключей */
    private final File file;
    /** Пароль хранилища ключей */
    private final String keyStorePassword;
    /** Тип хранилища ключей */
    private final String keyStoreType;
    /** Алиас пары ключей */
    private final String keyPassword;
    /** Пароль для приватного ключа */
    private final String keyAlias;

    @Override
    @SneakyThrows
    public SSLContext getSslContext() {
        if (!file.exists()) {
            return SSLContextBuilder.create().build();
        }
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        try (InputStream is = new FileInputStream(file)) {
            keystore.load(is, keyStorePassword.toCharArray());
        }

        return SSLContextBuilder
            .create()
            .loadKeyMaterial(keystore, keyPassword.toCharArray(), (aliases, socket) -> keyAlias)
            .build();
    }

    @Override
    @SneakyThrows
    public KeyPair getKeyPair() {
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        try (InputStream is = new FileInputStream(file)) {
            keystore.load(is, keyStorePassword.toCharArray());
        }
        Key key = keystore.getKey(keyAlias, keyPassword.toCharArray());
        Certificate cert = keystore.getCertificate(keyAlias);
        PublicKey publicKey = cert.getPublicKey();
        return new KeyPair(publicKey, (PrivateKey) key);
    }
}
