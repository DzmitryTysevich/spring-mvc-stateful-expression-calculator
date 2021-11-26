package com.epam.rd.autotasks.springstatefulcalc.calculator;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;

public interface CalculatorFactory {
    Calculator getCalculator(Analyzer analyzer);
}