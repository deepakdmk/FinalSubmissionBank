package Utility;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static boolean ifBgThanZero(BigDecimal balance) {
        return balance != null && balance.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static boolean isBetweenZeroAndHundred(BigDecimal value) {
        return value != null &&
                value.compareTo(BigDecimal.ZERO) >= 0 &&
                value.compareTo(BigDecimal.valueOf(100)) <= 0;
    }
}
