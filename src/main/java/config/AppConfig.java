package config;

import org.springframework.context.annotation.*;

/**
 * Main config
 **/
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan({"Utils.DAO","Services", "SQl", "aspects"})
@Import(DBConfig.class)
@EnableAspectJAutoProxy
public class AppConfig {
}
