package com.premiumcar.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = """
        {
          "status": 403,
          "message": "You are logged in as USER and do not have permission to access this resource",
          "path": "%s"
        }
        """.formatted(request.getRequestURI());

        response.getWriter().write(json);
        response.getWriter().flush();   // 🔥 REQUIRED
        response.getWriter().close();   // 🔥 REQUIRED
    }
}
