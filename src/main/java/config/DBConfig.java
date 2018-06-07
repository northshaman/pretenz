package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DB Configuration
 **/
@Configuration
@PropertySource("classpath:dbprops.properties")
public class DBConfig {
    @Value("${ds.pretenzUrl}")
    String pretenzUrl;
    @Value("${ds.pretenzUser}")
    String pretenzUser;
    @Value("${ds.pretenzPass}")
    String pretenzPass;
    @Value("${ds.ccbUrl}")
    String ccbUrl;
    @Value("${ds.ccbUser}")
    String ccbUser;
    @Value("${ds.ccbPass}")
    String ccbPass;
    @Value("${ds.driverName}")
    String driverName;

    @Bean
    public DataSource getPretenzDS(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(pretenzUrl,pretenzUser,pretenzPass);
        dataSource.setDriverClassName(driverName);
        return dataSource;
    }

    @Bean
    public Connection getPretenzConnection() throws SQLException{
        return getPretenzDS().getConnection();
    }

    @Bean @Qualifier(value = "pretenzJdbc")
    public JdbcTemplate getPretenzTemplate(){
        return new JdbcTemplate(getPretenzDS());
    }

    @Bean
    public DataSource getCCBDS(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(ccbUrl,ccbUser,ccbPass);
        dataSource.setDriverClassName(driverName);
        return dataSource;
    }

    @Bean @Qualifier(value = "ccbJdbc")
    public JdbcTemplate getCCBTemplate(){
        return new JdbcTemplate(getCCBDS());
    }
}
