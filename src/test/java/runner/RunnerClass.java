package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/featureFiles", 
					glue = "stepDefination", 
					dryRun = false, 
					monochrome = true, 
					strict = true, 
					tags = "@tag", 
					plugin = {
		"json:C:/Users/Vive.Kumar/eclipse-workspace1/cucumberTemplate/target/cucumber/1.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})

public class RunnerClass {

}