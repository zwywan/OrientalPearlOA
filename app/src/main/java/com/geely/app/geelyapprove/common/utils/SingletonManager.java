package com.geely.app.geelyapprove.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Oliver on 2016/9/28.
 */
public class SingletonManager {

    private static Map<String, Object> objectMap = new HashMap<>();

    private SingletonManager() {
    }

    public static void registerService(String key, Object obj) {
        if (!objectMap.containsKey(key)) {
            objectMap.put(key, obj);
        }
    }

    public static Object getService(String key) {
        return objectMap.get(key);
    }

    public static void unregisterService(String key) {
        if (objectMap.containsKey(key)) {
            objectMap.remove(key);
        }
    }

}
