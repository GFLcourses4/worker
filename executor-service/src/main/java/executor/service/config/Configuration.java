package executor.service.config;

import executor.service.maintenance.plugin.ScenarioExecutor;
import executor.service.maintenance.plugin.ScenarioExecutorImpl;
import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.exception.CantReadPropertiesException;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Config
public class Configuration {
    private final Properties properties;
    private static final String CONFIG_FILE_PATH = "/config.properties";


    public Configuration() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new CantReadPropertiesException(ex.getMessage());
        }
    }

    @Bean
    public WebDriverConfigDto webDriverConfigDto() {
        WebDriverConfigDto config = new WebDriverConfigDto();
        config.setWebDriverExecutable(properties.getProperty(PropertyKey.WEB_DRIVER_EXECUTABLE.getKey()));
        config.setUserAgent(properties.getProperty(PropertyKey.USER_AGENT.getKey()));
        config.setPageLoadTimeout(Long.parseLong(properties.getProperty(PropertyKey.PAGE_LOAD_TIMEOUT.getKey())));
        config.setImplicitlyWait(Long.parseLong(properties.getProperty(PropertyKey.IMPLICITLY_WAIT.getKey())));
        return config;
    }

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
    public ProxySourcesClient proxySourcesClient(){
        return new ProxySourcesClientImpl(new JsonProxySources());
    }
}
