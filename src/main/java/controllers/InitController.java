package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static Utils.ConfigManager.checkProperty;

/**
 * Init controller
 **/
@Controller
@RequestMapping("/")
public class InitController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView init(){
        if (checkProperty("lastUpdate", "is_updating"))
            return new ModelAndView("waitingPage");
        return new ModelAndView("main");
    }
}
