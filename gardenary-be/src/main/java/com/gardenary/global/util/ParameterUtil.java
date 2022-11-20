package com.gardenary.global.util;

public class ParameterUtil {

    public static boolean checkStringSize(int maxSize, String str) {
        if(str.length() > maxSize) {
            return false;
        }
        return true;
    }
}
