package executor.service.config;
import executor.service.ScenarioExecutor;
import executor.service.ScenarioExecutorImpl;
import executor.service.annotation.Bean;
import executor.service.annotation.Config;
import executor.service.exception.CantReadProperties;
import executor.service.maintenance.plugin.proxy.JsonProxySources;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.maintenance.plugin.proxy.ProxySourcesClientImpl;
import executor.service.model.WebDriverConfigDto;
import executor.service.logger.LoggingProxyProvider;
import executor.service.stepexecution.ClickCss;
import executor.service.stepexecution.ClickXpath;
import executor.service.stepexecution.Sleep;
import executor.service.stepexecution.StepExecution;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import java.util.List;

@Config
public class Configuration {
    private final PropertiesConfiguration properties;

    public Configuration() {
        try {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                    new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("config.properties"));
            properties = builder.getConfiguration();
        } catch (Exception ex) {
            throw new CantReadProperties(ex.getMessage());
        }
    }
    @Bean
    public WebDriverConfigDto webDriverConfigDto() {
        WebDriverConfigDto config = new WebDriverConfigDto();
        config.setWebDriverExecutable(properties.getString(PropertyKey.WEB_DRIVER_EXECUTABLE.getKey()));
        config.setUserAgent(properties.getString(PropertyKey.USER_AGENT.getKey()));
        config.setPageLoadTimeout(properties.getLong(PropertyKey.PAGE_LOAD_TIMEOUT.getKey()));
        config.setImplicitlyWait(properties.getLong(PropertyKey.IMPLICITLY_WAIT.getKey()));
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
    public ProxySourcesClient proxySourcesClient() {
        return new ProxySourcesClientImpl(new JsonProxySources());
    }
}
