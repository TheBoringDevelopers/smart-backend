package ru.the_boring_developers.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    /** Длина sms-подтверждения */
    private static final int SMS_CODE_LENGTH = 4;

    /** Длина идентификатора сессии */
    private static final int SESSION_ID_LENGTH = 9;

    public static String generateRandomNumbersString(int length) {
        int[] numbers = new SecureRandom().ints(0, 10).limit(length).toArray();
        StringBuilder code = new StringBuilder();
        for (int number : numbers) {
            code.append(number);
        }
        return code.toString();
    }

    public static String generateCode() {
        return generateRandomNumbersString(SMS_CODE_LENGTH);
    }

    public static String convertToJson(Object val) {
        try {
            return val != null ? new ObjectMapper().writeValueAsString(val) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String generateSessionId() {
        return generateRandomNumbersString(SESSION_ID_LENGTH);
    }

    public static String md5(String value) {
        try {
            return Hex.encodeHexString(MessageDigest.getInstance("MD5").digest(value.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
