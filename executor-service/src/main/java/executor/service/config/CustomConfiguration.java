package executor.service.config;

import executor.service.ScenarioExecutor;
import executor.service.ScenarioExecutorImpl;
import executor.service.annotation.Bean;
import executor.service.factory.webdriverinitializer.ChromeDriverProviderImpl;
import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.factory.webdriverinitializer.proxy.ProxyProviderImpl;
import executor.service.maintenance.plugin.proxy.JsonProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientImpl;
import executor.service.model.WebDriverConfigDto;
import executor.service.logger.LoggingProxyProvider;
import executor.service.stepexecution.ClickCss;
import executor.service.stepexecution.ClickXpath;
import executor.service.stepexecution.Sleep;
import executor.service.stepexecution.StepExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
//@PropertySource("classpath:application.properties")
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

//    @Bean
//    public ThreadPoolConfigDto threadPoolConfigDto() {
//        return PropertyReader.threadPoolConfigDtoFromProperties();
//    }

    @Bean
    public ScenarioExecutor scenarioExecutor() {
        Logger scenario_logger = LoggerFactory.getLogger("SCENARIO_LOGGER");
        List<StepExecution> steps = List.of(
                LoggingProxyProvider.createProxy(new ClickCss(), StepExecution.class, scenario_logger),
                LoggingProxyProvider.createProxy(new ClickXpath(), StepExecution.class, scenario_logger),
                LoggingProxyProvider.createProxy(new Sleep(), StepExecution.class, scenario_logger)
        );
        return LoggingProxyProvider.createProxy(new ScenarioExecutorImpl(steps), ScenarioExecutor.class, scenario_logger);
    }
    @Bean
    public ProxySourcesClient proxySourcesClient() {
        return new ProxySourcesClientImpl(new JsonProxySources());
    }
}