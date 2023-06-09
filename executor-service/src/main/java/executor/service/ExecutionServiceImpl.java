package executor.service;

import executor.service.maintenance.ScenarioExecutor;
import executor.service.model.ScenarioDto;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
