package com.epam.rd.autotasks.springstatefulcalc.calculator.webCalculator;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.calculator.Calculator;
import com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService;

import java.util.Map;

public class WebCalculatorImpl implements Calculator {
    private final Analyzer analyzer;
    private final CalculatorService calculatorService;

    public WebCalculatorImpl(Analyzer analyzer, CalculatorService calculatorService) {
        this.analyzer = analyzer;
        this.calculatorService = calculatorService;
    }

    public int calculate(Map<String, String> cacheData) {
        return analyzer.getExpressionResult(calculatorService.getExpressionWithParamValue(cacheData));
    }
}