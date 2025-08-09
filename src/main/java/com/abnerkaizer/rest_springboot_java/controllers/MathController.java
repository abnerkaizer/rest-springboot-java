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
    //http://localhost:8080/math/sub/3/5
    @RequestMapping("/sub/{num1}/{num2}")
    public Double sub(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (!isNumeric(num1) || !isNumeric(num2)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(num1) - convertToDouble(num2);
    }
    //http://localhost:8080/math/mult/3/5
    @RequestMapping("/multi/{num1}/{num2}")
    public Double multi(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (!isNumeric(num1) || !isNumeric(num2)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return convertToDouble(num1) * convertToDouble(num2);
    }
    //http://localhost:8080/math/div/3/5
    @RequestMapping("/div/{num1}/{num2}")
    public Double div(@PathVariable("num1") String num1, @PathVariable("num2") String num2) throws Exception{
        if (!isNumeric(num1) || !isNumeric(num2)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        Double number1 = convertToDouble(num1);
        Double number2 = convertToDouble(num2);
        if (number2==0) throw new UnsupportedMathOperationException("Please set number 2 as non zero value!");
        return number1/number2;
    }

    //http://localhost:8080/math/mean/3/5
    @RequestMapping("/mean/{num1}/{num2}")
    public Double mean(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (!isNumeric(num1) || !isNumeric(num2)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return (convertToDouble(num1) + convertToDouble(num2))/2;
    }

    //http://localhost:8080/math/sqrt/81
    @RequestMapping("/sqrt/{num}")
    public Double sqrt(
            @PathVariable("num") String num
    ) throws Exception {
        if (!isNumeric(num)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return Math.sqrt(convertToDouble(num));
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
}
