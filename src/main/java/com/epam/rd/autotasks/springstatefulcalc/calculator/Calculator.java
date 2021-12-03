package com.epam.rd.autotasks.springstatefulcalc.calculator;

import java.util.Map;

public interface Calculator {
    int calculate (Map<String, String> cacheData);
}