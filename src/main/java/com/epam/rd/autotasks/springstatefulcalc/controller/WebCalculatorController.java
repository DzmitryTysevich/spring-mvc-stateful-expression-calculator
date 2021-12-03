package com.epam.rd.autotasks.springstatefulcalc.controller;

import com.epam.rd.autotasks.springstatefulcalc.cacheData.CacheData;
import com.epam.rd.autotasks.springstatefulcalc.config.WebCalculatorConfig;
import com.epam.rd.autotasks.springstatefulcalc.constant.PathConstants;
import com.epam.rd.autotasks.springstatefulcalc.exception.BadRequestException;
import com.epam.rd.autotasks.springstatefulcalc.service.CalculatorService;
import com.epam.rd.autotasks.springstatefulcalc.service.WebService;
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

import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.EXPRESSION;
import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.OVER_RANGE;
import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.RESULT;
import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.WRONG_EXPRESSION;

@Controller
public class WebCalculatorController {

    private final WebService webService;
    private final CalculatorService calculatorService;
    private final CacheData cacheData;

    public WebCalculatorController(WebService webService, CalculatorService calculatorService, CacheData cacheData) {
        this.webService = webService;
        this.calculatorService = calculatorService;
        this.cacheData = cacheData;
    }

    @PutMapping(PathConstants.MULTI_CALC_PATH)
    public void putValue(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Model model) throws BadRequestException, IOException {
        HttpSession session = httpServletRequest.getSession();
        try (BufferedReader bufferedReader = httpServletRequest.getReader()) {
            String paramNameFromURL = webService.getParamNameFromURL(httpServletRequest);
            String paramValue = bufferedReader.readLine();

            session.setAttribute(paramNameFromURL, paramValue);

            if (EXPRESSION.equalsIgnoreCase(paramNameFromURL)) {
                if (calculatorService.isGoodFormatExpression(paramValue)) {
                    webService.addDataToCache(httpServletResponse, session, paramNameFromURL);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, WRONG_EXPRESSION);
                }
            } else {
                if (!calculatorService.isParameterHasOverLimitValue(paramValue)) {
                    webService.addDataToCache(httpServletResponse, session, paramNameFromURL);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, OVER_RANGE);
                }
            }
            model.addAttribute(paramNameFromURL, session.getAttribute(paramNameFromURL));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(PathConstants.RESULT_PATH)
    @ResponseStatus(value = HttpStatus.OK)
    public String getResult(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            WebCalculatorConfig calculatorConfig,
                            Model model) throws IOException {
        Map<String, Map<String, String>> data = cacheData.getCacheData();
        HttpSession session = httpServletRequest.getSession();

        int result = calculatorConfig.calculator().calculate(data.get(session.getId()));
        session.setAttribute(RESULT, result);
        model.addAttribute(RESULT, session.getAttribute(RESULT));

        PrintWriter pw = httpServletResponse.getWriter();
        pw.printf(session.getAttribute(RESULT).toString());
        pw.close();
        return PathConstants.RESULT_PATH;
    }

    @DeleteMapping(PathConstants.MULTI_CALC_PATH)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteValue(HttpServletRequest httpServletRequest, HttpSession session) {
        String paramNameFromURL = webService.getParamNameFromURL(httpServletRequest);
        session.removeAttribute(paramNameFromURL);
        cacheData.getCacheData().get(session.getId()).remove(paramNameFromURL);
    }
}