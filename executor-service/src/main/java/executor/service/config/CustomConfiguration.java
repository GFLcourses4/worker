package executor.service.config;

import executor.service.factory.webdriverinitializer.ChromeDriverProviderImpl;
import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.factory.webdriverinitializer.proxy.ProxyProviderImpl;
import executor.service.maintenance.plugin.proxy.JsonProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientImpl;
import executor.service.model.WebDriverConfigDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.ComponentScan;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("executor.service")
public class CustomConfiguration {

    private final Environment env;

    public CustomConfiguration(Environment env) {
        this.env = env;
    }
    @Bean
    public WebDriverConfigDto webDriverConfigDto() {
        String webDriverExecutable = env.getProperty("executorservice.common.webDriverExecutable");
        String userAgent = env.getProperty("executorservice.common.userAgent");
        Long pageLoadTimeout = Long.valueOf(env.getProperty("executorservice.common.pageLoadTimeout"));
        Long implicitlyWait = Long.valueOf(env.getProperty("executorservice.common.driverWait"));

        return new WebDriverConfigDto(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
    }

    @Bean
    public WebDriverProvider driverProvider() {
        return new ChromeDriverProviderImpl(new ProxyProviderImpl(), webDriverConfigDto());
    }

    @Bean
    public ProxySourcesClient proxySourcesClient() {
        return new ProxySourcesClientImpl(new JsonProxySources());
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("PROJECT_LOGGER");
    }
}