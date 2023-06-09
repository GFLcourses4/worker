package executor.service.stepexecution;

import executor.service.exception.ClickXPathException;
import executor.service.model.StepDto;
import org.openqa.selenium.*;

public class ClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, StepDto stepDto) {
        try {
            webDriver.findElement(By.xpath(stepDto.getValue())).click();
        } catch (NoSuchElementException | ElementNotInteractableException
                 | StaleElementReferenceException | TimeoutException ex) {
            throw new ClickXPathException(ex.getMessage());
        }
    }
}
