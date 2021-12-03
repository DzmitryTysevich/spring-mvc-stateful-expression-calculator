package com.epam.rd.autotasks.springstatefulcalc.calculator.calculatorFactory;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.calculator.Calculator;
import com.epam.rd.autotasks.springstatefulcalc.calculator.CalculatorFactory;
import com.epam.rd.autotasks.springstatefulcalc.calculator.webCalculator.WebCalculatorImpl;
import com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService;

public class WebCalculatorFactoryImpl implements CalculatorFactory {
    @Override
    public Calculator getCalculator(Analyzer analyzer, CalculatorService calculatorService) {
        return new WebCalculatorImpl(analyzer, calculatorService);
    }
}