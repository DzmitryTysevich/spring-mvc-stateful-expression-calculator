package com.epam.rd.autotasks.springstatefulcalc.calculator;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService;

public interface CalculatorFactory {
    Calculator getCalculator(Analyzer analyzer, CalculatorService calculatorService);
}