package Utils.DAO.ccbToPretenz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * class for transferring data from ccb to pretenz
 **/
@Component
public class CCBToPretenzTransferer {
    private JdbcTemplate jdbcTemplate;
    private Connection connectionPretenz;

    @Autowired
    public CCBToPretenzTransferer(@Qualifier(value = "ccbJdbc") JdbcTemplate jdbcTemplate, Connection connectionPretenz) {
        this.jdbcTemplate = jdbcTemplate;
        this.connectionPretenz = connectionPretenz;
    }

    /**
     * transferring data from ccb to pretenz db
     * @param query - query statement to ccb
     * @param insert - insert statement to pretenz
     */
    public void insertIntoPretenz(String query, String insert){
        PretenzResultSetInserter pretenzResultSetInserter = new PretenzResultSetInserter(connectionPretenz);
        pretenzResultSetInserter.setQuery(insert);
        jdbcTemplate.query(query, pretenzResultSetInserter);
    }
}
