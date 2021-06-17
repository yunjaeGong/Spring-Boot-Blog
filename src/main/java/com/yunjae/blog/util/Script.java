package com.yunjae.blog.util;

public class Script {
    public static String back(String message) {
        // 경고창을 띄우고 뒤로
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('" + message + "')");
        sb.append("history.back()");
        sb.append("</script>");
        return sb.toString();
    }
}
