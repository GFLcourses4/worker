package executor.service.model;

import java.util.List;
import java.util.Objects;

public class ScenarioDto {
    private String name;
    private String site;
    private List<StepDto> steps;

    public ScenarioDto() {}

    public ScenarioDto(String name, String site, List<StepDto> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScenarioDto scenario = (ScenarioDto) o;
        return Objects.equals(name, scenario.name) &&
                Objects.equals(site, scenario.site) &&
                Objects.equals(steps, scenario.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, site, steps);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", steps=" + steps +
                '}';
    }

}
