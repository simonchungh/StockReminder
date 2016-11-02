package utils;

/**
 * Created by simonchung on 1/12/15.
 */
public class Utils {
    private static int STOCK_LENGTH = 6;
    static public boolean isValidStockNumber(String number) {
        if (number.equals("")) {
            return false;
        } else if (!isNumeric(number)) {
            return false;
        } else if (number.length() != STOCK_LENGTH) {
            return false;
        }
        return true;
    }
    static public boolean isValidPrice(String price) {
        if (price.equals("")) {
            return false;
        } else if (!isNumeric(price)) {
            return false;
        }
        return true;
    }
    private static boolean isNumeric(String str) {
        try
        {
            double d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
