/* @Author: Mila (s223313)
   @Author: Hildibjørg (s164539)
 */
package behaviourtests;

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