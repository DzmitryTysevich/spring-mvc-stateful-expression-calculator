package com.epam.rd.autotasks.springstatefulcalc.service;

import com.epam.rd.autotasks.springstatefulcalc.exception.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.rd.autotasks.springstatefulcalc.cacheData.CacheData.SINGLETON_DATA;
import static com.epam.rd.autotasks.springstatefulcalc.constant.Constants.*;

public class AppControllerService {
    public static final AppControllerService APP_CONTROLLER_SERVICE = new AppControllerService();

    private AppControllerService() {
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
            SINGLETON_DATA.getCacheData().put(session.getId(), new ConcurrentHashMap<>());
        }
        if (SINGLETON_DATA.getCacheData().get(session.getId()).get(paramName) == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_CREATED);
        }
        SINGLETON_DATA.getCacheData().get(session.getId()).put(paramName, session.getAttribute(paramName).toString());
    }
}