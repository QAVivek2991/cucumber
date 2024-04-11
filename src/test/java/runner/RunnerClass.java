package runner;



import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "./src/test/resources/featureFiles", 
					glue = "stepDefinition", 
					dryRun = true, 
					monochrome = true, 
					strict = true, 
					//tags = "@runthis", 
					plugin = {
		"json:C:/Users/Vive.Kumar/eclipse-workspace1/cucumberTemplate/target/cucumber/1.json"
							})

public class RunnerClass {
	

}