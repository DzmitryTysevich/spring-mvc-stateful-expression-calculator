package com.epam.rd.autotasks.springstatefulcalc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface CalculatorControllerService {
    boolean isParameterHasOverLimitValue(String paramValue);

    boolean isGoodFormatExpression(String expression);

    boolean isDigit(String paramValue);

    String getParamNameFromURL(HttpServletRequest httpServletRequest);

    void addDataToCache(HttpServletResponse httpServletResponse, HttpSession session, String paramName) throws IOException;
}