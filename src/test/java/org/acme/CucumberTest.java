package org.acme;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

/* Important: 
For Cucumber tests to be recognized by Maven, the class name has to have
either the word Test or Tests in the beginning or at the end. 
For example, the class name CucumberTestExample will be ignored by Maven.
*/

@RunWith(Cucumber.class)
@CucumberOptions(plugin="summary"
			   , publish= false
			   , features = "features"  // directory of the feature files
			   , snippets = SnippetType.CAMELCASE
			   )
public class CucumberTest {
}
