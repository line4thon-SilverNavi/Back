package org.likelion._thon.silver_navi.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class EncryptConfig {

    @Value("${encryptor.secret-key}")
    private String secretKey;

    @Value("${encryptor.salt}")
    private String salt;

    @Bean
    public TextEncryptor textEncryptor() {
        // AES/GCM (256bit) 기반의 강력한 암호화/복호화 객체를 생성합니다.
        return Encryptors.text(secretKey, salt);
    }
}
