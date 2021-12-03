package com.epam.rd.autotasks.springstatefulcalc.config;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.Analyzer;
import com.epam.rd.autotasks.springstatefulcalc.analyzer.analyzerFactory.LexemeAnalyzerFactoryImpl;
import com.epam.rd.autotasks.springstatefulcalc.calculator.Calculator;
import com.epam.rd.autotasks.springstatefulcalc.calculator.webCalculator.WebCalculatorImpl;
import com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService;
import com.epam.rd.autotasks.springstatefulcalc.service.calculatorService.WebCalculatorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.epam.rd.autotasks.springstatefulcalc")
public class WebCalculatorConfig {

    @Bean
    public Analyzer lexemeAnalyzer() {
        return new LexemeAnalyzerFactoryImpl().getAnalyzer();
    }

    @Bean
    public CalculatorService calculatorService() {
        return new WebCalculatorServiceImpl();
    }

    @Bean
    @Scope("prototype")
    public Calculator calculator() {
        return new WebCalculatorImpl(lexemeAnalyzer(), calculatorService());
    }
}