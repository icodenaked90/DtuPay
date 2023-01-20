package behaviourtests;

/*
This file is copied from the "Correlation Student Registration Example" zip file.
Created by Hubert Baumeister.
Accessed on 2023-01-11
 */
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin="summary",
        snippets = SnippetType.CAMELCASE,
        features="features")
public class CucumberTest {
}