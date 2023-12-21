package runner;



import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
			features = "./src/test/java/featureFiles",
			glue = "stepDefination",
			dryRun = false,
			monochrome = true,
			strict = true,
			tags = "@runthis"
		)


public class RunnerClass {

}
