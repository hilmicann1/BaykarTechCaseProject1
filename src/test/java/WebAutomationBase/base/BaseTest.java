package WebAutomationBase.base;

import com.thoughtworks.gauge.AfterScenario;

import com.thoughtworks.gauge.BeforeScenario;

import com.thoughtworks.gauge.ExecutionContext;
import io.github.bonigarcia.wdm.WebDriverManager;

import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.firefox.FirefoxProfile;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.remote.LocalFileDetector;

import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;

import java.util.concurrent.TimeUnit;




public class BaseTest {

  protected static WebDriver driver;

  protected static WebDriverWait webDriverWait;

  private static Logger logger = LoggerFactory.getLogger(BaseTest.class);

  public static String browserName="chrome";

  DesiredCapabilities capabilities = new DesiredCapabilities();


  public static String getHTML() throws Exception {
    StringBuilder result = new StringBuilder();
    URL url = new URL("https://operasyon-dev.s3.eu-west-1.amazonaws.com/1.0.0_0.crx");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    logger.info("connection established");
    conn.setRequestMethod("GET");
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()))) {
      for (String line; (line = reader.readLine()) != null; ) {
        result.append(line);
      }
    }
    return result.toString();
  }

  @BeforeScenario

  public void setUp(ExecutionContext context) throws Exception {

    String baseUrl = "https://Kariyer.baykartech.com";
    String selectPlatform = "mac";
    String selectBrowser = "chrome";

    if (StringUtils.isEmpty(System.getenv("key"))){
      if ("win".equalsIgnoreCase(selectPlatform)){
        if ("chrome".equalsIgnoreCase(selectBrowser)){
          ChromeOptions options = new ChromeOptions();
          /*options.AddUserProfilePreference("profile.default_content_setting_values.cookies", 2);*/

          Map<String, Object> prefs = new LinkedHashMap<>();
          prefs.put("credentials_enable_service", Boolean.valueOf(false));
          prefs.put("profile.password_manager_enabled", Boolean.valueOf(false));
          options.setExperimentalOption("prefs", prefs);

          capabilities = DesiredCapabilities.chrome();
//          Map<String, Object> prefs = new HashMap<String, Object>();
//          prefs.put("profile.default_content_setting_values.notifications", 2);
//          options.setExperimentalOption("prefs", prefs);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          options.addArguments("--disable-popup-blocking");
          options.addArguments("--start-fullscreen");
          WebDriverManager.chromedriver().setup();
          driver = new ChromeDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);


        }else if ("firefox".equalsIgnoreCase(selectBrowser)){
          FirefoxOptions options = new FirefoxOptions();
          capabilities = DesiredCapabilities.firefox();
          Map<String, Object> prefs = new HashMap<String, Object>();
          prefs.put("profile.default_content_setting_values.notifications", 2);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          FirefoxProfile profile = new FirefoxProfile();
          capabilities.setCapability(FirefoxDriver.PROFILE,profile);
          capabilities.setCapability("marionette",true);
          System.setProperty("webdriver.gecko.driver","webdriver/geckodriver.exe");
          driver = new FirefoxDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        }


      }else if("mac".equalsIgnoreCase(selectPlatform)){
        if ("chrome".equalsIgnoreCase(selectBrowser)){
          ChromeOptions options = new ChromeOptions();
          capabilities = DesiredCapabilities.chrome();
          Map<String, Object> prefs = new HashMap<String, Object>();
          prefs.put("profile.default_content_setting_values.notifications", 2);
          options.setExperimentalOption("prefs", prefs);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          options.addArguments("--start-fullscreen");
          System.setProperty("webdriver.chrome.driver","web_driver/chromedriver");
          driver = new ChromeDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        }else if ("firefox".equalsIgnoreCase(selectBrowser)){
          FirefoxOptions options = new FirefoxOptions();
          capabilities = DesiredCapabilities.firefox();
          Map<String, Object> prefs = new HashMap<String, Object>();
          prefs.put("profile.default_content_setting_values.notifications", 2);
          options.addArguments("--kiosk");
          options.addArguments("--disable-notifications");
          options.addArguments("--start-fullscreen");
          FirefoxProfile profile = new FirefoxProfile();
          capabilities.setCapability(FirefoxDriver.PROFILE,profile);
          capabilities.setCapability("marionette",true);
          System.setProperty("webdriver.gecko.driver","web_driver/geckodriver");
          driver = new FirefoxDriver(options);
          driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);
        }

      }

    }



    driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);

    driver.manage().window().maximize();

    driver.get(baseUrl);

  }

  @AfterScenario

  public void tearDown() {
    driver.quit();

  }

}