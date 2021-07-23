package com.assertthat;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/report/surefire-reports/cucumber/cucumber.json"},
        features = {"src/test/resources/com/assertthat/features"})
public class RunCucumberIT {
}