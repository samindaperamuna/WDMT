/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;

/**
 *
 * @author Saminda Permauna
 */
public class DataValidator {

    /**
     * Checks whether the given number in the decimal format is a valid number.
     *
     * @param s - number in String format
     * @return
     */
    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    /**
     * Checks whether the given number with the base radix is a valid number.
     *
     * @param s - number in string format
     * @param radix - radix of the number
     * @return
     */
    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }

            if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check whether given value is a double.
     * 
     * @param s
     * @return 
     */
    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    public static boolean isValidTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");

        try {
            sdf.parse(date);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
