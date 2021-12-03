package com.epam.rd.autotasks.springstatefulcalc.service;

import java.util.Map;

public interface CalculatorService {
    String getExpressionWithParamValue(Map<String, String> cacheData);
}