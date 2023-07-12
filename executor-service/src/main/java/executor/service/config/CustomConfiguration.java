package executor.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("executor.service")
public class CustomConfiguration {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("PROJECT_LOGGER");
    }
}