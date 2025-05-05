package utils;

import java.math.BigDecimal;

public class SumUtils {
    public static BigDecimal toDecimal(String price) {
        try {
            return new BigDecimal(price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
