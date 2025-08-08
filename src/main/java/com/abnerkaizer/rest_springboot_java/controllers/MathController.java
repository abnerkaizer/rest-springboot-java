package com.abnerkaizer.rest_springboot_java.controllers;

import com.abnerkaizer.rest_springboot_java.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    //http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{num1}/{num2}")
    public Double sum(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (!isNumeric(num1) || !isNumeric(num2)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(num1) + convertToDouble(num2);
    }

    private Double convertToDouble(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
    //http://localhost:8080/math/sub/3/5
    //http://localhost:8080/math/div/3/5

}
