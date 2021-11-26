package com.epam.rd.autotasks.springstatefulcalc.cacheData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheData {
    public static final CacheData SINGLETON_DATA = new CacheData();
    private final Map<String, Map<String, String>> dataMap = new ConcurrentHashMap<>();

    private CacheData() {
    }

    public Map<String, Map<String, String>> getCacheData() {
        return dataMap;
    }
}