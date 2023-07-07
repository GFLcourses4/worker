package executor.service;

import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

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
    public void runInParallelFlowTest() {
        WebDriver driver = Mockito.mock(WebDriver.class);
        ProxyConfigHolderDto proxy = mock(ProxyConfigHolderDto.class);

        when(proxySourcesClient.getProxy()).thenReturn(proxy);
        when(threadPoolConfigDto.getCorePoolSize()).thenReturn(NUMBER_OF_THREADS);
        when(driverProvider.create(proxy)).thenReturn(driver);

        parallelFlowExecutor.runInParallelFlow();

        verify(proxySourcesClient, times(NUMBER_OF_GET_PROXY_CALL)).getProxy();
        verify(scenarioSourceListener, times(NUMBER_OF_EXECUTE_CALL)).execute();
        verify(executionService, times(NUMBER_OF_THREADS))
                .execute(eq(driver), eq(scenarioSourceListener), eq(scenarioExecutor));
    }
}
