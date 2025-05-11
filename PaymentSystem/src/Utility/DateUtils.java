package Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
    private static final String DATE_FORMAT_TRANSACTION = "yyyyMMdd";

    public static boolean isValidDate(String dateStr) {
        if (StringUtils.isEmpty(dateStr) || dateStr.length() != 8) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TRANSACTION);
        /*This is just for me to make sure the date is valid
        so I may use Date to calculate interested in future code*/
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String makeDayDoubleDigits(int dateStr){
        return String.format("%02d", dateStr);
    }
}
