package executor.service;

import executor.service.maintenance.plugin.scenario.JsonScenarioSource;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonScenarioSourceTest {

    @Test
    public void testGetScenariosIfFileNamePassedInConstructor() {
        JsonScenarioSource reader = new JsonScenarioSource("scenarios.json");
        assertEquals(getScenariosList(), reader.getScenarios());
    }

    @Test
    public void testGetScenariosIfFileCorrectLocatedInResources() {
        JsonScenarioSource reader = new JsonScenarioSource();
        assertEquals(getScenariosList(), reader.getScenarios());
    }

    public List<ScenarioDto> getScenariosList() {
        StepDto firstStepFirstScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");
        StepDto secondStepFirstScenario = new StepDto("sleep", "5:10");
        StepDto thirdStepFirstScenario = new StepDto("clickXpath", "/html/body/p");

        List<StepDto> stepsFirstScenario = List.of(firstStepFirstScenario, secondStepFirstScenario, thirdStepFirstScenario);
        ScenarioDto firstScenario = new ScenarioDto("test scenario 1", "http://info.cern.ch", stepsFirstScenario);

        StepDto firstStepSecondScenario = new StepDto("clickXpath", "/html/body/p");
        StepDto secondStepSecondScenario = new StepDto("sleep", "5:10");
        StepDto thirdStepSecondScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");

        List<StepDto> stepsSecondScenario = List.of(firstStepSecondScenario, secondStepSecondScenario, thirdStepSecondScenario);
        ScenarioDto secondScenario = new ScenarioDto("test scenario 2", "http://info.cern.ch", stepsSecondScenario);

        return List.of(firstScenario, secondScenario);
    }
}
