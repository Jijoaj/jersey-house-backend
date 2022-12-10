package com.jijo.jerseyhouse.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jijo
 * Common utility methods used accross the application
 */
@Slf4j
public final class CommonMethods {
    private CommonMethods() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }
    private static final Map<String, String> result= new HashMap<>();

    public static String getStringIfNull(Object obj){
        return !ObjectUtils.isEmpty(obj) ? obj.toString() : "";
    }

    public static Map<String,String> getSuccessMapResponse(){
        result.put("status", "success");
        return result;
    }

    public static Map<String,String> getFailureMapResponse(){
        result.put("status", "failed");
        return result;
    }
}
