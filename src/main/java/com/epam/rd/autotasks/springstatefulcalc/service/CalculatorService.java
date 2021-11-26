package com.epam.rd.autotasks.springstatefulcalc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.epam.rd.autotasks.springstatefulcalc.constant.Constants.EMPTY_STRING;
import static com.epam.rd.autotasks.springstatefulcalc.constant.Constants.EXPRESSION;

public class CalculatorService {
    public static final CalculatorService CALCULATOR_SERVICE = new CalculatorService();

    private CalculatorService() {
    }

    public String[] getExpressionAsArray(Map<String, String> cacheData) {
        return cacheData.get(EXPRESSION).split(EMPTY_STRING);
    }

    public boolean isDigit(String value) {
        boolean flag = false;
        char[] valueAsChars = value.toCharArray();
        for (char c : valueAsChars) {
            if (Character.isDigit(c)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String getExpressionWithParamValue(Map<String, String> cacheData) {
        List<String> expressionList = new ArrayList<>(List.of(getExpressionAsArray(cacheData)));
        IntStream.range(0, expressionList.size())
                .filter(i -> cacheData.containsKey(expressionList.get(i)))
                .forEach(i -> {
                    String paramValue = cacheData.get(expressionList.get(i));
                    if (isDigit(paramValue)) {
                        expressionList.set(i, paramValue);
                    } else {
                        String nestedValue = cacheData.get(paramValue);
                        expressionList.set(i, nestedValue);
                    }
                });
        return String.join(EMPTY_STRING, expressionList);
    }
}