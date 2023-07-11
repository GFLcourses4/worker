package executor.service.utl;

import executor.service.config.PropertyKey;
import executor.service.model.ThreadPoolConfigDto;
import executor.service.model.WebDriverConfigDto;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertyReader {
    private static final Configuration config;

    static {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(params.properties().setFileName("application.properties"));
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebDriverConfigDto webDriverConfigDtoFromProperties(){
        return new WebDriverConfigDto(
                config.getString(PropertyKey.WEB_DRIVER_EXECUTABLE.getKey()),
                config.getString(PropertyKey.USER_AGENT.getKey()),
                config.getLong(PropertyKey.PAGE_LOAD_TIMEOUT.getKey()),
                config.getLong(PropertyKey.IMPLICITLY_WAIT.getKey()));
    }

    public static ThreadPoolConfigDto threadPoolConfigDtoFromProperties() {
        return new ThreadPoolConfigDto(config.getInt(PropertyKey.CORE_POOL_SIZE.getKey()),
                config.getLong(PropertyKey.KEEP_ALIVE_TIME.getKey()));
    }
}