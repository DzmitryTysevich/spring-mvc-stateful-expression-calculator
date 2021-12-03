package com.epam.rd.autotasks.springstatefulcalc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface WebService {

    String getParamNameFromURL(HttpServletRequest httpServletRequest);

    void addDataToCache(HttpServletResponse httpServletResponse, HttpSession session, String paramName) throws IOException;
}