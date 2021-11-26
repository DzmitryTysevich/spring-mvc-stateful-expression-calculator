package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.analyzer.analyzerFactory.LexemeAnalyzerFactoryImpl;
import com.epam.rd.autotasks.springstatefulcalc.calculator.calculatorFactory.WebCalculatorFactoryImpl;
import com.epam.rd.autotasks.springstatefulcalc.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.epam.rd.autotasks.springstatefulcalc.cacheData.CacheData.SINGLETON_DATA;
import static com.epam.rd.autotasks.springstatefulcalc.constant.Constants.*;
import static com.epam.rd.autotasks.springstatefulcalc.service.AppControllerService.APP_CONTROLLER_SERVICE;

@Controller
public class WebCalculatorController {

    @PutMapping(MULTI_CALC_PATH)
    public void putValue(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Model model) throws BadRequestException, IOException {
        HttpSession session = httpServletRequest.getSession();
        try (BufferedReader bufferedReader = httpServletRequest.getReader()) {
            String paramNameFromURL = APP_CONTROLLER_SERVICE.getParamNameFromURL(httpServletRequest);
            String paramValue = bufferedReader.readLine();

            session.setAttribute(paramNameFromURL, paramValue);

            if (EXPRESSION.equalsIgnoreCase(paramNameFromURL)) {
                if (APP_CONTROLLER_SERVICE.isGoodFormatExpression(paramValue)) {
                    APP_CONTROLLER_SERVICE.addDataToCache(httpServletResponse, session, paramNameFromURL);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, WRONG_EXPRESSION);
                }
            } else {
                if (!APP_CONTROLLER_SERVICE.isParameterHasOverLimitValue(paramValue)) {
                    APP_CONTROLLER_SERVICE.addDataToCache(httpServletResponse, session, paramNameFromURL);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, OVER_RANGE);
                }
            }
            model.addAttribute(paramNameFromURL, session.getAttribute(paramNameFromURL));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(RESULT_PATH)
    @ResponseStatus(value = HttpStatus.OK)
    public String getResult(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            WebCalculatorFactoryImpl webCalculatorFactoryImpl,
                            LexemeAnalyzerFactoryImpl lexemeAnalyzerFactoryImpl,
                            Model model) throws IOException {
        Map<String, Map<String, String>> cacheData = SINGLETON_DATA.getCacheData();
        HttpSession session = httpServletRequest.getSession();

        int result = webCalculatorFactoryImpl.getCalculator(lexemeAnalyzerFactoryImpl.getAnalyzer()).calculate(cacheData.get(session.getId()));

        session.setAttribute(RESULT, result);
        model.addAttribute(RESULT, session.getAttribute(RESULT));

        PrintWriter pw = httpServletResponse.getWriter();
        pw.printf(session.getAttribute(RESULT).toString());
        pw.close();
        return RESULT_PATH;
    }

    @DeleteMapping(MULTI_CALC_PATH)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteValue(HttpServletRequest httpServletRequest, HttpSession session) {
        String paramNameFromURL = APP_CONTROLLER_SERVICE.getParamNameFromURL(httpServletRequest);
        session.removeAttribute(paramNameFromURL);
        SINGLETON_DATA.getCacheData().get(session.getId()).remove(paramNameFromURL);
    }
}