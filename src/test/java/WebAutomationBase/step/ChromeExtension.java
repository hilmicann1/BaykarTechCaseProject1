package WebAutomationBase.step;

import WebAutomationBase.base.BaseTest;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;

import org.openqa.selenium.interactions.Actions;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;


public class ChromeExtension extends BaseTest {
    private static Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory
            .getLogger(BaseSteps.class);

    private Actions actions = new Actions(driver);

    public ChromeExtension() {
        PropertyConfigurator
                .configure(BaseSteps.class.getClassLoader().getResource("log4j.properties"));
    }



    private void getExtensionDriver(){
        driver.get("chrome-extension://eecaejegccegjjjngokgelmehfkmpico/index.html");
    }




        private void checkChromeExtensionText(){
            try {
                driver.findElement(By.xpath("//div[text()=' Extension JSON file']"));
            }
            catch (Exception e){
                Assert.fail("Extension file text is not exist");
            }
            BaseSteps.checkLastUpdatedDateTime();
        }
    }

