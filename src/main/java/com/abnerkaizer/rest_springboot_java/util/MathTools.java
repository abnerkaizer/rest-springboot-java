package com.abnerkaizer.rest_springboot_java.util;

import com.abnerkaizer.rest_springboot_java.exception.UnsupportedMathOperationException;

public class MathTools {
    public static Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return true;
        String number = strNumber.replaceAll(",", ".");
        return !number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
