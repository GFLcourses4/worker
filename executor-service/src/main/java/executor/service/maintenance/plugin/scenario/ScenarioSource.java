package executor.service.maintenance.plugin.scenario;

import executor.service.model.ScenarioDto;

import java.util.List;

public interface ScenarioSource {
    List<ScenarioDto> getScenarios();
}
