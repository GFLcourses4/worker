package executor.service.factory.webdriverinitializer;

import org.springframework.stereotype.Component;
import executor.service.factory.webdriverinitializer.proxy.ProxyProvider;
import executor.service.factory.webdriverinitializer.setting.UserAgentArgument;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.WebDriverConfigDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;

@Component
public class ChromeDriverProviderImpl implements WebDriverProvider {
    private final ProxyProvider proxyProvider;
    private final WebDriverConfigDto webDriverConfig;

    public ChromeDriverProviderImpl(ProxyProvider proxyProvider, WebDriverConfigDto webDriverConfig) {
        this.proxyProvider = proxyProvider;
        this.webDriverConfig = webDriverConfig;
    }

    @Override
    public WebDriver create(ProxyConfigHolderDto proxyConfigHolder) {
        ChromeOptions options = createChromeOptions(proxyConfigHolder);
        return createChromeDriver(options);
    }

    @Override
    public WebDriver create() {
        return create(null);
    }

    private WebDriver createChromeDriver(ChromeOptions options) {
        try (ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(webDriverConfig.getWebDriverExecutable()))
                .build()) {
            ChromeDriver driver = new ChromeDriver(service, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(webDriverConfig.getImplicitlyWait()));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(webDriverConfig.getPageLoadTimeout()));
            return driver;
        }
    }
    private ChromeOptions createChromeOptions(ProxyConfigHolderDto proxyConfigHolder) {
        ChromeOptions options = new ChromeOptions();
        if(proxyConfigHolder != null) options.setProxy(proxyProvider.getProxy(proxyConfigHolder));
        options.addArguments(UserAgentArgument.CHROME.getArgument() + webDriverConfig.getUserAgent());
        return options;
    }

}
