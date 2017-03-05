package com.my.zakupki.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {

    public static double round4digits(double source)
    {
        return (double)Math.round(10000*source)/10000;
    }

    public static double round2digits(double source)
    {
        return (double)Math.round(100*source)/100;
    }

    public static double parseDoubleSafely(String str) {
        double result = 0;
        try {
            result = Double.parseDouble(str.replace(",", "."));
        } catch (NullPointerException npe) {
        } catch (NumberFormatException nfe) {
        }
        return result;
    }

    public static long parseLongSafely(String str) {
        long result = 0;
        try {
            result = Long.parseLong(str);
        } catch (NullPointerException npe) {
        } catch (NumberFormatException nfe) {
            try {
                result = (long) Double.parseDouble(str.replace(",", "."));
            } catch (NullPointerException ignored) {
            } catch (NumberFormatException ignored) {
            }
        }
        return result;
    }

    public static int parseIntSafely(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (Exception ignore) {
            result = (int)parseDoubleSafely(str);
        }
        return result;
    }

    public static boolean parseBooleanSafely(String str) {
        boolean result = false;
        if (str.trim().toLowerCase().equals("true"))
            result = true;
        return result;
    }

    public static boolean isDoubleValue(String str) {
        try {
            Double.parseDouble(str);
        } catch (NullPointerException npe) {
            return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isLongValue(String str) {
        try {
            Long.parseLong(str);
        } catch (NullPointerException npe) {
            return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isAllDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static String NumberFormat(double number) {
        return NumberFormatDelimeter(number);
    }

    public static String NumberFormatDelimeter(double number) {
        String res = "";
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00", decimalFormatSymbols);
        res = decimalFormat.format(number);
        if (res.substring(res.length() - 3).equals(".00"))
            res = res.substring(0, res.length() - 3);
        return res;
    }

    public static String NumberFormatWithoutDelimeter(double number) {
        String res = "";
        res = String.format(Locale.US, "%.2f", number);
        if (res.substring(res.length() - 3).equals(".00"))
            res = res.substring(0, res.length() - 3);
        return res;
    }

    public static String ClearPrice(String input) {
        return String.format(Locale.US, "%.2f", NumberUtils.parseDoubleSafely(input));
    }

    public static String ClearCoupon(String input) {
        String res = String.format(Locale.US, "%.3f", NumberUtils.parseDoubleSafely(input));

        if (res.length()>3 && res.contains(".") && res.substring(res.length() - 3).equals("000"))
            res = res.substring(0, res.length() - 3);

        if (res.length()>2 && res.contains(".") && res.substring(res.length() - 2).equals("00"))
            res = res.substring(0, res.length() - 2);

        if (res.length()>1 && res.contains(".") && res.substring(res.length() - 1).equals("0"))
            res = res.substring(0, res.length() - 1);

        if (res.length()>0 && res.charAt(res.length() - 1)=='.')
            res = res.replace(".", "");

        return res;
    }

}
