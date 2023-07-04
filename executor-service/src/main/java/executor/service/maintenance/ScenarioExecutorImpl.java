package executor.service.maintenance;

import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import executor.service.stepexecution.StepExecution;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final Map<String, StepExecution> stepExecutionMap;

    public ScenarioExecutorImpl(List<StepExecution> steps) {
        this.stepExecutionMap = new ConcurrentHashMap<>(steps.stream()
                .collect(Collectors.toMap(StepExecution::getStepAction, Function.identity())));
    }

    @Override
    public void execute(ScenarioDto scenarioDto, WebDriver webDriver) {
        webDriver.get(scenarioDto.getSite());
        Consumer<StepDto> executeStep = v -> stepExecutionMap
                .get(v.getAction())
                .step(webDriver, v);
        scenarioDto.getSteps().forEach(executeStep);
    }
}
