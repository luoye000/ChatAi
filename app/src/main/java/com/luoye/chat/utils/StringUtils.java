package com.luoye.chat.utils;

/**
 * Created by: luoye
 * Time: 2023/3/27
 * user:
 */
public class StringUtils {


    /**
     * key 掩藏显示
     *
     * @param key
     * @return
     */
    public static String aipKeyShow(String key) {
        return maskString(key, "*", 7);
    }

    /**
     * 需要隐藏字符
     *
     * @param input
     * @param start
     * @return
     */
    public static String maskString(String input, String s, int start) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        int length = input.length();
        if (start < 0 || start >= length) {
            return input;
        }
        String prefix = input.substring(0, start);
        String masked = input.substring(start).replaceAll(".", s);
        return prefix + masked;
    }
}
