package com.microtech.smartshop.interceptor;

import com.microtech.smartshop.enums.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("USER_ID") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.getWriter().write("Unauthorized: Please log in.");
            return false;
        }

        Role userRole = (Role) session.getAttribute("USER_ROLE");
        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (userRole == Role.ADMIN) {
            return true;
        }

        if (userRole == Role.CLIENT) {

            if (!method.equals("GET")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                response.getWriter().write("Acces denied : Action is not allowed for CLIENT role.");
                return false;
            }

            Long sessionClientId = (Long) session.getAttribute("CLIENT_ID");

            if (uri.startsWith("/api/clients")) {
                if (uri.equals("/api/clients") || uri.equals("/api/clients/")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
                if (uri.matches(".*/api/clients/\\d+$")) {
                    String idStr = uri.substring(uri.lastIndexOf("/") + 1);
                    try {
                        Long requestedId = Long.parseLong(idStr);
                        if (!requestedId.equals(sessionClientId)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }

            if (uri.startsWith("/api/orders")) {
                if (uri.equals("/api/orders") || uri.equals("/api/orders/")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }

                if (uri.contains("/client/")) {
                    String idStr = uri.substring(uri.lastIndexOf("/") + 1);
                    try {
                        Long requestedId = Long.parseLong(idStr);
                        if (!requestedId.equals(sessionClientId)) {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }


            return true;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}