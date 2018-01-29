package fr.knowledge.acceptance.config;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "fr.sharingcraftsman.knowledge.acceptance"
)
public class RunCukesTest {
}
