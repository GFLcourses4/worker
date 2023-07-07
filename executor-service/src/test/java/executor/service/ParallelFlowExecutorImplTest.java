package executor.service;

import executor.service.factory.webdriverinitializer.WebDriverProvider;
import executor.service.maintenance.plugin.proxy.ProxySourcesClient;
import executor.service.model.ProxyConfigHolderDto;
import executor.service.model.ThreadPoolConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        ArgumentCaptor<WebDriver> driverCaptor = ArgumentCaptor.forClass(WebDriver.class);
        ArgumentCaptor<ScenarioSourceListener> listenerCaptor = ArgumentCaptor.forClass(ScenarioSourceListener.class);
        ArgumentCaptor<ScenarioExecutor> executorCaptor = ArgumentCaptor.forClass(ScenarioExecutor.class);

        parallelFlowExecutor.runInParallelFlow();

        verify(executionService, times(NUMBER_OF_THREADS))
                .execute(driverCaptor.capture(), listenerCaptor.capture(), executorCaptor.capture());

        List<WebDriver> capturedDrivers = driverCaptor.getAllValues();
        List<ScenarioSourceListener> capturedListeners = listenerCaptor.getAllValues();
        List<ScenarioExecutor> capturedExecutors = executorCaptor.getAllValues();

        assertEquals(NUMBER_OF_THREADS, capturedDrivers.size());
        assertEquals(NUMBER_OF_THREADS, capturedListeners.size());
        assertEquals(NUMBER_OF_THREADS, capturedExecutors.size());

        verify(proxySourcesClient, times(NUMBER_OF_GET_PROXY_CALL)).getProxy();
        verify(scenarioSourceListener, times(NUMBER_OF_EXECUTE_CALL)).execute();
    }
}