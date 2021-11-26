package com.epam.rd.autotasks.springstatefulcalc.calculator.calculatorFactory;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.calculator.Calculator;
import com.epam.rd.autotasks.springstatefulcalc.calculator.CalculatorFactory;
import com.epam.rd.autotasks.springstatefulcalc.calculator.webCalculator.WebCalculatorImpl;

public class WebCalculatorFactoryImpl implements CalculatorFactory {
    @Override
    public Calculator getCalculator(Analyzer analyzer) {
        return new WebCalculatorImpl(analyzer);
    }
}