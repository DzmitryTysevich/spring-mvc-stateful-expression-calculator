package com.epam.rd.autotasks.springstatefulcalc.analyzer.analyzerFactory;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.analyzer.AnalyzerFactory;
import com.epam.rd.autotasks.springstatefulcalc.analyzer.lexemeAnalyzer.LexemeAnalyzerImpl;

public class LexemeAnalyzerFactoryImpl implements AnalyzerFactory {

    @Override
    public Analyzer getAnalyzer() {
        return new LexemeAnalyzerImpl();
    }
}