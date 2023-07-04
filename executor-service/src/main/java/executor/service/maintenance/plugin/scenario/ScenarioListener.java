package executor.service.maintenance.plugin.scenario;

import executor.service.annotation.Component;
import executor.service.model.ScenarioDto;

import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class ScenarioListener implements ScenarioSourceListener {
    private final ScenarioSource scenarioSource;
    private Deque<ScenarioDto> scenarios;

    public ScenarioListener(ScenarioSource scenarioSource) {
        this.scenarioSource = scenarioSource;
    }

    @Override
    public void prepareScenarios() {
        scenarios = new ConcurrentLinkedDeque<>(scenarioSource.getScenarios());
    }

    @Override
    public Optional<ScenarioDto> getScenario() {
        return Optional.ofNullable(scenarios.pollFirst());
    }
}
