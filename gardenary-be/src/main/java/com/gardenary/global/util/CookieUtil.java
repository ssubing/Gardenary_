package com.gardenary.global.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void setRefreshTokenCookie(HttpServletResponse res, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        // 2주
        cookie.setMaxAge(14 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static void deleteRefreshTokenCookie(HttpServletResponse res) {
        Cookie myCookie = new Cookie("refreshToken", null);
        // 쿠키의 expiration 타임을 0으로 하여 삭제
        myCookie.setMaxAge(0);

        // 모든 경로에서 삭제됐음을 알림
        myCookie.setPath("/");
        res.addCookie(myCookie);
    }

    public static String searchCookie(HttpServletRequest req, String searchName) {
        Cookie[] cookies = req.getCookies();
        String findCookie = "";

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(searchName)) {
                    findCookie = c.getValue().trim();
                    break;
                }
            }
        }
        return findCookie;
    }
}
