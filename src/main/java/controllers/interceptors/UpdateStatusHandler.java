package controllers.interceptors;

import Utils.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Filter for setting status of update
 **/
public class UpdateStatusHandler extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger("pretenzLogFile");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Setting lastUpdate property");
        ConfigManager.SetProperty("lastUpdate", "is_updating");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Setting lastUpdate property");
        if (response.getStatus() == 200)
            ConfigManager.SetProperty("lastUpdate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        else
            ConfigManager.SetProperty("lastUpdate", "error");
    }
}
