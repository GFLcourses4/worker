package executor.service.maintenance.plugin;

import executor.service.maintenance.plugin.scenario.ScenarioSourceListener;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                 ScenarioExecutor scenarioExecutor);
}
