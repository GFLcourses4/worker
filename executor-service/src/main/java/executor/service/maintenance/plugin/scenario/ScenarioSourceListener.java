package executor.service.maintenance.plugin.scenario;

import executor.service.model.ScenarioDto;

import java.util.Optional;

public interface ScenarioSourceListener {
    void prepareScenarios();
    Optional<ScenarioDto> getScenario();
}
