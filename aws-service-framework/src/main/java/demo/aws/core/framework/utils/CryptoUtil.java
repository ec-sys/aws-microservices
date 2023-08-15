package demo.aws.core.framework.utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoUtil {
    public static String bcryptEncode(String rawData) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawData);
    }

    public static boolean isMatchBCryptEncoded(String rawData, String encodedData) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawData, encodedData);
    }
}
