package com.example.mock_project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieHelper {

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) throws IOException, UnsupportedEncodingException {
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
    public static void removeCookie(HttpServletResponse response, String name)  {
        Cookie cookie = new Cookie(name,"");
        cookie.setMaxAge( 0 );
        cookie.setPath("/");
        response.addCookie( cookie );
    }
}
