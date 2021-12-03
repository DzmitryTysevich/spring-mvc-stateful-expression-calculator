package com.epam.rd.autotasks.springstatefulcalc.service;

import java.util.Map;

public interface CalculatorService {
    String getExpressionWithParamValue(Map<String, String> cacheData);

    boolean isDigit(String value);

    boolean isParameterHasOverLimitValue(String paramValue);

    boolean isGoodFormatExpression(String expression);
}