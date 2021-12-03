package com.epam.rd.autotasks.springstatefulcalc.service.webService;

import com.epam.rd.autotasks.springstatefulcalc.cacheData.CacheData;
import com.epam.rd.autotasks.springstatefulcalc.exception.BadRequestException;
import com.epam.rd.autotasks.springstatefulcalc.service.WebService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.BAD_URL;
import static com.epam.rd.autotasks.springstatefulcalc.constant.CommonConstants.URL_SUBSTRING;

@Service
public class WebServiceImpl implements WebService {
    private final CacheData cacheData;

    public WebServiceImpl(CacheData cacheData) {
        this.cacheData = cacheData;
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