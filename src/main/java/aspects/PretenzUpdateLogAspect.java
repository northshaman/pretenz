package aspects;

import Utils.TimerForLogs;
import controllers.PretenzUpdateController;
import models.QueryDateParams;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Asepcts config class
 **/
@Component
@Aspect
public class PretenzUpdateLogAspect {
    private Logger logger = LoggerFactory.getLogger("updateLog");
    private Logger rootLogger = LoggerFactory.getLogger("pretenzLogger");

    @Around("execution(* controllers.PretenzUpdateController.*(models.QueryDateParams)) && args(dateParams)")
    public Object fullUpdate(ProceedingJoinPoint point, QueryDateParams dateParams) throws Throwable {
        logger.info("**************************************************************************\n\n");

        if (point.getSignature().getName().equals("recreateTable"))
            logger.info("MONTHLY TABLE RECREATION STARTED ");
        else if (point.getSignature().getName().equals("updateTable"))
            logger.info("DAILY TABLE UPDATE STARTED: ");


        TimerForLogs timerForLogs = new TimerForLogs();
        Object res = point.proceed();
        logger.info("UPDATE CYCLE DONE IN: " + timerForLogs.getRound() / 60 + " minutes");
        return res;
    }

    @Before("execution(* SQl.SQLScenarioMapRepository.setScenarioMap(models.QueryDateParams)) && args(dateParams)")
    public void setQueryParamDates(QueryDateParams dateParams) {
        logger.info("Otchet month: " + dateParams.getOtchetMonth() + "." + dateParams.getOtchetYear());
        logger.info("Current month: " + dateParams.getCurrentMonth() + "." + dateParams.getCurrentYear());
    }

    @Before("execution(* controllers.PretenzUpdateController.servletError(Exception)) && args(ex)")
    public void updateError(Exception ex){
        logger.error(ex.getMessage());
    }

    @Before("execution(* Utils.DAO.PretenzDAO.clearTable(String)) && args(tableName)")
    public void clearTable(String tableName){
        logger.info("Clearing table " + tableName + "");
    }

    @Around("execution(* Utils.DAO.ccbToPretenz.CCBToPretenzTransferer.insertIntoPretenz(..))")
    public Object insertIntoPretenz(ProceedingJoinPoint point) throws Throwable{
        String tableName = Thread.currentThread().getName();
        TimerForLogs timer = new TimerForLogs();
        Object res = point.proceed();
        logger.info("Insert from ccb to " + tableName + " in: " + timer.getRound() + "s.");
        return res;
    }

    @Around("execution(* Utils.DAO.PretenzDAO.makeTransaction(..))")
    public Object transferFromCache(ProceedingJoinPoint point) throws Throwable{
        String tableName = Thread.currentThread().getName();
        TimerForLogs timer = new TimerForLogs();
        Object res = point.proceed();
        logger.info("Transfer from " + tableName + " to CM_PRETENZ_ALL in: " + timer.getRound() + "s.");
        return res;
    }

    @Around("execution(* Utils.DAO.dbfToPretenz.DBFToPretenzTransferer.makeInsertFromDBF(..)) && args(..,fileName)")
    public Object insertingFromDBF(ProceedingJoinPoint point, String fileName) throws Throwable {
        logger.info("Start of inserting from dbf file: " + fileName);
        TimerForLogs timer = new TimerForLogs();
        Object res = point.proceed();
        logger.info("Insert from " + fileName + " is done in: " + timer.getRound() + "s.");
        return res;
    }

    @Around("execution(* Services.TableUpdaterImpl.clearInsertUpdate(.., String)) && args(tableName)")
    public Object clearInsertUpdate(ProceedingJoinPoint point, String tableName) throws Throwable{
        logger.info(tableName + " update started");
        TimerForLogs timer = new TimerForLogs();
        Object res = point.proceed();
        logger.info("Updated from " + tableName + " in: " + timer.getRound() + "s.");
        return res;
    }

    @Before("execution(* controllers.ExceptionHandlingController.servletError(Exception)) && args(ex)")
    public void errorHandler(Exception ex){
        rootLogger.error(ex.getMessage());
    }

    @Around("execution(* Utils.DAO.PretenzDAO.callProcedure(String)) && args(procName)")
    public Object updateReculc(ProceedingJoinPoint point, String procName) throws Throwable{
        logger.info("Starting " + procName);
        TimerForLogs timerForLogs = new TimerForLogs();
        Object res = point.proceed();
        logger.info(procName + " procedure done in: " + timerForLogs.getRound());
        return res;
    }
}
