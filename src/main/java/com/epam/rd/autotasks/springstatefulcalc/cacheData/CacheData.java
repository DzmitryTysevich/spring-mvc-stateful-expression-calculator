package com.epam.rd.autotasks.springstatefulcalc.cacheData;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CacheData {
    private final Map<String, Map<String, String>> dataMap = new ConcurrentHashMap<>();

    public Map<String, Map<String, String>> getCacheData() {
        return dataMap;
    }
}