package executor.service.factory.webdriverinitializer.proxy;

import org.springframework.stereotype.Component;
import executor.service.model.ProxyConfigHolderDto;
import org.openqa.selenium.Proxy;
@Component
public class ProxyProviderImpl implements ProxyProvider {
    private static final String HTTP_PROXY_DELIMITER = ":";
    @Override
    public Proxy getProxy(ProxyConfigHolderDto proxyConfigHolder) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyConfigHolder.getProxyNetworkConfig().getHostname()
                + HTTP_PROXY_DELIMITER
                + proxyConfigHolder.getProxyNetworkConfig().getPort());
        if (proxyConfigHolder.getProxyCredentials() != null) {
            proxy.setSocksUsername(proxyConfigHolder.getProxyCredentials().getUsername());
            proxy.setSocksPassword(proxyConfigHolder.getProxyCredentials().getPassword());
        }
        return proxy;
    }
}
