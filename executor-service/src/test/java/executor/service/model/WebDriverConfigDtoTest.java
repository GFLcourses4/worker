package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebDriverConfigDtoTest {
    private WebDriverConfigDto config;

    @BeforeEach
    public void setup() {
        config = new WebDriverConfigDto("chromedriver", "desktop", 30000L, 3000L);
    }

    @Test
    public void testEmptyConstructor() {
        WebDriverConfigDto emptyConfig = new WebDriverConfigDto();
        Assertions.assertNull(emptyConfig.getWebDriverExecutable());
        Assertions.assertNull(emptyConfig.getUserAgent());
        Assertions.assertNull(emptyConfig.getPageLoadTimeout());
        Assertions.assertNull(emptyConfig.getImplicitlyWait());
    }

    @Test
    public void testGettersAndSetters() {
        Assertions.assertEquals("chromedriver", config.getWebDriverExecutable());
        Assertions.assertEquals("desktop", config.getUserAgent());
        Assertions.assertEquals(30000L, config.getPageLoadTimeout());
        Assertions.assertEquals(3000L, config.getImplicitlyWait());

        config.setWebDriverExecutable("geckodriver");
        config.setUserAgent("mobile");
        config.setPageLoadTimeout(20000L);
        config.setImplicitlyWait(2000L);

        Assertions.assertEquals("geckodriver", config.getWebDriverExecutable());
        Assertions.assertEquals("mobile", config.getUserAgent());
        Assertions.assertEquals(20000L, config.getPageLoadTimeout());
        Assertions.assertEquals(2000L, config.getImplicitlyWait());
    }

    @Test
    public void testEquals() {
        WebDriverConfigDto sameConfig = new WebDriverConfigDto("chromedriver", "desktop", 30000L, 3000L);
        WebDriverConfigDto differentConfig = new WebDriverConfigDto("geckodriver", "mobile", 20000L, 2000L);

        Assertions.assertEquals(config, sameConfig);
        Assertions.assertEquals(sameConfig, config);
        Assertions.assertNotEquals(config, differentConfig);
        Assertions.assertNotEquals(differentConfig, config);
    }
}

