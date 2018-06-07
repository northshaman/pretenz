package controllers.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static Utils.ConfigManager.checkProperty;

/**
 * Filter for checking if pretenz update is currently running
 **/
public class UpdateStatusCheckingHandler extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkProperty("lastUpdate", "is_updating"))
            throw new ServletException("Table is currently updating");
        return true;
    }
}
