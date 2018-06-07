package controllers;

import SQl.SQLScenarioMapRepository;
import Services.PretenzService;
import Utils.ConfigManager;
import models.QueryDateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Controller for updating or recreating pretenz table
 **/
@Controller
@CrossOrigin(value = "*")
@RequestMapping(value = "/table")
public class PretenzUpdateController {
    private final PretenzService pretenzService;
    private final SQLScenarioMapRepository mapRepository;

    private Logger logger = LoggerFactory.getLogger("pretenzLogFile");

    @Autowired
    public PretenzUpdateController(PretenzService pretenzService, SQLScenarioMapRepository mapRepository) {
        this.pretenzService = pretenzService;
        this.mapRepository = mapRepository;
    }

    @RequestMapping(value = "/recreate",
            method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public void recreateTable(QueryDateParams queryDateParams) throws ServletException, InterruptedException, SQLException{
        mapRepository.setScenarioMap(queryDateParams);
        pretenzService.fullTableReload(queryDateParams.getOtchetMonth());
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public void updateTable(QueryDateParams queryDateParams) throws InterruptedException{
        mapRepository.setScenarioMap(queryDateParams);
        pretenzService.dailyTableUpdate();
    }

    @ExceptionHandler(value = {ServletException.class, IOException.class, SQLException.class})
    public @ResponseBody
    String servletError(Exception ex){
        logger.error(ex.getMessage());
        ConfigManager.SetProperty("lastUpdate", "error");
        return ex.getMessage();
    }

}
