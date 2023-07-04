package executor.service.maintenance;

import executor.service.annotation.Component;
import executor.service.maintenance.plugin.scenario.ScenarioSourceListener;
import executor.service.model.ScenarioDto;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

@Component
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void execute(WebDriver webDriver, ScenarioSourceListener scenarioSourceListener,
                        ScenarioExecutor scenarioExecutor) {

        Optional<ScenarioDto> scenario = scenarioSourceListener.getScenario();

        while(scenario.isPresent()) {
            scenarioExecutor.execute(scenario.get(), webDriver);
            scenario = scenarioSourceListener.getScenario();
        }

        webDriver.close();
    }

}
