package com.situ.anime.security.utils;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author liangyunfei
 */
public class WebUtils {
    @SuppressWarnings("CallToPrintStackTrace")
    public static String renderString(HttpServletResponse response, String str){
        try{
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
