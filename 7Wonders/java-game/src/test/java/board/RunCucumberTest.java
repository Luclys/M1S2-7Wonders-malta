package board;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(plugin ={"pretty"}, features ="src/test/resources/cucumber")
public class RunCucumberTest { }