package com.epam.rd.autotasks.springstatefulcalc.calculator.webCalculator;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.calculator.Calculator;

import java.util.Map;

import static com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService.CALCULATOR_SERVICE;

public class WebCalculatorImpl implements Calculator {
    private final Analyzer analyzer;

    public WebCalculatorImpl(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public int calculate(Map<String, String> cacheData) {
        return analyzer.getExpressionResult(CALCULATOR_SERVICE.getExpressionWithParamValue(cacheData));
    }
}