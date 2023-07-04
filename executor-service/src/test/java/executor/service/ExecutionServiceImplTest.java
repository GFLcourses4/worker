package executor.service;

import executor.service.maintenance.ExecutionService;
import executor.service.maintenance.ExecutionServiceImpl;
import executor.service.maintenance.ScenarioExecutor;
import executor.service.maintenance.ScenarioExecutorImpl;
import executor.service.maintenance.plugin.scenario.ScenarioListener;
import executor.service.maintenance.plugin.scenario.ScenarioSourceListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class ExecutionServiceImplTest {

    private ExecutionService executionService;

    private WebDriver webDriver;

    private ScenarioSourceListener scenarioSourceListener;

    private ScenarioExecutor scenarioExecutor;


    @BeforeEach
    void setUp() {
        this.executionService = Mockito.mock(ExecutionServiceImpl.class);
        this.webDriver = Mockito.mock(ChromeDriver.class);
        this.scenarioSourceListener = Mockito.mock(ScenarioListener.class);
        this.scenarioExecutor = Mockito.mock(ScenarioExecutorImpl.class);
    }


    @Test
    void testSuccessfulExecution() {
        this.executionService.execute(this.webDriver, this.scenarioSourceListener, this.scenarioExecutor);

        Mockito.verify(this.executionService, Mockito.times(1))
                .execute(this.webDriver, this.scenarioSourceListener, this.scenarioExecutor);
    }

    @Test
    void testFailureExecution() {
        String errorMessage = "Execution failed";

        Mockito.doThrow(RuntimeException.class).when(this.executionService)
                .execute(this.webDriver, this.scenarioSourceListener, this.scenarioExecutor);

        Assertions.assertThrows(RuntimeException.class,
                () -> this.executionService.execute(this.webDriver, this.scenarioSourceListener, this.scenarioExecutor),
                errorMessage);
    }

    
}
