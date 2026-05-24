package org.example.irctc.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.irctc.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//steps to set middlewares-> middleware(interceptor) creation -> in config file to register this jwt interceptor(config)
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{


        String authHeader=request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           response.setStatus(401);
           response.getWriter().write("Unauthorized: Missing or invalid token format");
            return false;
        }

        String accessToken=authHeader.substring(7);
        if (!JwtUtil.validateToken(accessToken)) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized: Token has expired or is invalid");
            return false;
        }

        String userId = JwtUtil.extractUserId(accessToken);
        request.setAttribute("userId", userId);
        return true;
    }

}
