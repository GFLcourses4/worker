package executor.service.maintenance.plugin.scenario;

import executor.service.annotation.Component;
import executor.service.model.ScenarioDto;
import executor.service.utl.JsonReader;

import java.util.List;

@Component
public class JsonScenarioSource implements ScenarioSource {
    private String resourceName = "scenarios.json";

    public JsonScenarioSource() {}

    public JsonScenarioSource(String newResourceName) {
        resourceName = newResourceName;
    }

    public List<ScenarioDto> getScenarios() {
        return JsonReader.parseResourceToList(resourceName, ScenarioDto.class);
    }
}
