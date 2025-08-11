package com.abnerkaizer.rest_springboot_java.controllers;

import com.abnerkaizer.rest_springboot_java.exception.UnsupportedMathOperationException;
import com.abnerkaizer.rest_springboot_java.math.Operations;
import com.abnerkaizer.rest_springboot_java.util.MathTools;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    private final Operations math = new Operations();

    //http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{num1}/{num2}")
    public Double sum(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (MathTools.isNumeric(num1) || MathTools.isNumeric(num2))
            throw new UnsupportedMathOperationException("Please set a numeric value!");


        return math.sum(MathTools.convertToDouble(num1), MathTools.convertToDouble(num2));
    }

    //http://localhost:8080/math/sub/3/5
    @RequestMapping("/sub/{num1}/{num2}")
    public Double sub(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (MathTools.isNumeric(num1) || MathTools.isNumeric(num2))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.sub(MathTools.convertToDouble(num1), MathTools.convertToDouble(num2));
    }

    //http://localhost:8080/math/multi/3/5
    @RequestMapping("/multi/{num1}/{num2}")
    public Double multi(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (MathTools.isNumeric(num1) || MathTools.isNumeric(num2))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.multi(MathTools.convertToDouble(num1), MathTools.convertToDouble(num2));
    }

    //http://localhost:8080/math/div/3/5
    @RequestMapping("/div/{num1}/{num2}")
    public Double div(@PathVariable("num1") String num1, @PathVariable("num2") String num2) throws Exception {
        if (MathTools.isNumeric(num1) || MathTools.isNumeric(num2))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        Double number1 = MathTools.convertToDouble(num1);
        Double number2 = MathTools.convertToDouble(num2);
        if (number2 == 0) throw new UnsupportedMathOperationException("Please set number 2 as non zero value!");
        return math.div(MathTools.convertToDouble(num1), MathTools.convertToDouble(num2));
    }

    //http://localhost:8080/math/mean/3/5
    @RequestMapping("/mean/{num1}/{num2}")
    public Double mean(
            @PathVariable("num1") String num1,
            @PathVariable("num2") String num2
    ) throws Exception {
        if (MathTools.isNumeric(num1) || MathTools.isNumeric(num2))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.mean(MathTools.convertToDouble(num1), MathTools.convertToDouble(num2));
    }

    //http://localhost:8080/math/sqrt/81
    @RequestMapping("/sqrt/{num}")
    public Double sqrt(
            @PathVariable("num") String num
    ) throws Exception {
        if (MathTools.isNumeric(num)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.sqrt(MathTools.convertToDouble(num));
    }
}
