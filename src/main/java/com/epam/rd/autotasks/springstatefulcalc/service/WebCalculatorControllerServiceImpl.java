package com.epam.rd.autotasks.springstatefulcalc.service;

import com.epam.rd.autotasks.springstatefulcalc.cacheData.CacheData;
import com.epam.rd.autotasks.springstatefulcalc.exception.BadRequestException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.*;

@Service
public class WebCalculatorControllerServiceImpl implements CalculatorControllerService {
    private final CacheData cacheData;

    public WebCalculatorControllerServiceImpl(CacheData cacheData) {
        this.cacheData = cacheData;
    }

    public boolean isParameterHasOverLimitValue(String paramValue) {
        return isDigit(paramValue) && Math.abs(Integer.parseInt(paramValue)) > TEN_THOUSAND;
    }

    public boolean isGoodFormatExpression(String expression) {
        return (expression.contains(PLUS) || expression.contains(MINUS) ||
                expression.contains(DIVIDE) || expression.contains(MULTIPLY));
    }

    public boolean isDigit(String paramValue) {
        char[] paramValueChars = paramValue.toCharArray();
        ArrayList<Character> arrayList = new ArrayList<>();
        for (char paramValueChar : paramValueChars) {
            if (paramValueChar != MINUS_CHAR) {
                arrayList.add(paramValueChar);
            }
        }
        return arrayList.stream().allMatch(Character::isDigit);
    }

    public String getParamNameFromURL(HttpServletRequest httpServletRequest) {
        String itemParamName = httpServletRequest.getRequestURI();
        String substring;
        if (!itemParamName.isEmpty() || !itemParamName.trim().isEmpty()) {
            substring = itemParamName.substring(URL_SUBSTRING);
        } else {
            throw new BadRequestException(BAD_URL);
        }
        return substring;
    }

    public void addDataToCache(HttpServletResponse httpServletResponse, HttpSession session, String paramName) throws IOException {
        if (session.isNew()) {
            cacheData.getCacheData().put(session.getId(), new ConcurrentHashMap<>());
        }
        if (cacheData.getCacheData().get(session.getId()).get(paramName) == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_CREATED);
        }
        cacheData.getCacheData().get(session.getId()).put(paramName, session.getAttribute(paramName).toString());
    }
}