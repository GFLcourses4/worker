package executor.service;

import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class ParallelFlowExecutorImplTest {

    private ExecutionService executionService;
    private ScenarioSourceListener scenarioSourceListener;
    private WebDriverProvider driverProvider;
    private ThreadPoolConfigDto threadPoolConfigDto;
    private ProxySourcesClient proxySourcesClient;
    private ScenarioExecutor scenarioExecutor;
    private ParallelFlowExecutorImpl parallelFlowExecutor;
    private static final int NUMBER_OF_THREADS = 1;
    private static final int NUMBER_OF_GET_PROXY_CALL = 1;
    private static final int NUMBER_OF_EXECUTE_CALL = 1;

    @BeforeEach
    void setUp() {
        executionService = Mockito.mock(ExecutionService.class);
        scenarioSourceListener = Mockito.mock(ScenarioSourceListener.class);
        driverProvider = Mockito.mock(WebDriverProvider.class);
        threadPoolConfigDto = Mockito.mock(ThreadPoolConfigDto.class);
        proxySourcesClient = Mockito.mock(ProxySourcesClient.class);
        scenarioExecutor = Mockito.mock(ScenarioExecutor.class);
        parallelFlowExecutor = new ParallelFlowExecutorImpl(executionService, scenarioSourceListener,
                driverProvider, threadPoolConfigDto, proxySourcesClient, scenarioExecutor);
    }

    @Test
    public void runInParallelFlowTest() throws InterruptedException {
        ProxyConfigHolderDto proxyConfig = new ProxyConfigHolderDto();
        when(proxySourcesClient.getProxy()).thenReturn(proxyConfig);

        int corePoolSize = 10;
        long keepAliveTime = 500;
        when(threadPoolConfigDto.getCorePoolSize()).thenReturn(corePoolSize);
        when(threadPoolConfigDto.getKeepAliveTime()).thenReturn(keepAliveTime);

        WebDriver driver = Mockito.mock(WebDriver.class);
        when(driverProvider.create(proxyConfig)).thenReturn(driver);

        parallelFlowExecutor.runInParallelFlow();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();

        verify(proxySourcesClient, times(1)).getProxy();
        verify(scenarioSourceListener, times(1)).execute();
        verify(threadPoolConfigDto, atLeastOnce()).getCorePoolSize();
        verify(threadPoolConfigDto, times(1)).getKeepAliveTime();
        verify(driverProvider, times(corePoolSize)).create(proxyConfig);
        verify(executionService, times(corePoolSize)).execute(eq(driver), eq(scenarioSourceListener), eq(scenarioExecutor));
    }


}
