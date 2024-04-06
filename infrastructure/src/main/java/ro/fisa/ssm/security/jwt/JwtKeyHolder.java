package ro.fisa.ssm.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ro.fisa.ssm.exceptions.AppRuntimeException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created at 4/6/2024 by Darius
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtKeyHolder {

    private static final SecretKey SECRET_KEY;

    static{

        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            final SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(256, secureRandom);
            SECRET_KEY = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new AppRuntimeException(e);
        }
    }

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}
