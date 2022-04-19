package io.github.pixee.ccf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

final class DeserializeReportTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void it_deserializes_input() throws IOException {
    String inputJson =
        "{  \"artifact\" : \"/path/to/semmle.sarif\" , \"sha1\" : \"154F\", \"vendor\" : \"semmle\"}";
    CCFInput input = mapper.readValue(inputJson, CCFInput.class);
    assertThat(input.getArtifact(), equalTo("/path/to/semmle.sarif"));
    assertThat(input.getSha1(), equalTo("154F"));
    assertThat(input.getVendor(), equalTo("semmle"));
  }

  @Test
  void it_deserializes_basic_report() throws IOException {
    File file = new File("src/test/resources/basic.json");
    CCFReport report = mapper.readValue(file, CCFReport.class);

    CCFRun run = report.getRun();
    assertThat(run.getCommandLine(), equalTo("pixee change . --dry-run"));
    assertThat(run.getElapsed(), equalTo(105024L));
    assertThat(run.getTool(), equalTo("pixee-cli"));
    assertThat(run.getVendor(), equalTo("pixee"));
    List<CCFFileExtensionScanned> filesScanned = run.getFilesScanned();
    assertThat(filesScanned.size(), equalTo(3));

    assertThat(filesScanned.get(0).getCount(), equalTo(156));
    assertThat(filesScanned.get(0).getExtension(), equalTo("java"));
    assertThat(filesScanned.get(1).getCount(), equalTo(7));
    assertThat(filesScanned.get(1).getExtension(), equalTo("js"));
    assertThat(filesScanned.get(2).getCount(), equalTo(128));
    assertThat(filesScanned.get(2).getExtension(), equalTo("xml"));

    assertThat(run.getFailedFiles(), hasItems("/foo/failed.java"));

    CCFConfiguration configuration = run.getConfiguration();
    assertThat(configuration.getDirectory(), equalTo("/tmp/path/to/repository/"));

    List<String> includes = configuration.getIncludes();
    assertThat(includes, hasItems("/path/to/include.java"));
    List<String> excludes = configuration.getExcludes();
    assertThat(excludes, hasItems("/path/to/exclude.js:52"));

    List<CCFInput> inputs = configuration.getInputs();
    CCFInput input = inputs.get(0);
    assertThat(input.getArtifact(), equalTo("/tmp/path/to/semmle.sarif"));
    assertThat(input.getVendor(), equalTo("Semmle/v1.2"));
    assertThat(input.getSha1(), equalTo("2F5A14..."));

    List<String> modules = configuration.getModules();
    assertThat(modules, hasItems("java/v1.2", "javascript/v5.0"));

    List<CCFResult> results = report.getResults();
    CCFResult ccfResult = results.get(0);
    assertThat(ccfResult.getDiff(), equalTo("... udiff text..."));
    assertThat(ccfResult.getPath(), equalTo("src/main/java/org/acme/Foo.java"));

    List<CCFChange> changes = ccfResult.getChanges();
    CCFChange firstChange = changes.get(0);
    assertThat(firstChange.getCategory(), equalTo("java-deserialization-hardening"));
    assertThat(
        firstChange.getDescription(),
        equalTo(
            "Added a call to ObjectInputStream#setObjectFilter() to prevent known malicious gadgets.."));
    assertThat(firstChange.getLineNumber(), equalTo(153));

    CCFChange secondChange = changes.get(1);
    assertThat(secondChange.getCategory(), equalTo("java-secure-randomness"));
    assertThat(
        secondChange.getDescription(),
        equalTo("Replaced a call to java.util.Random with java.secure.SecureRandom"));
    assertThat(secondChange.getLineNumber(), equalTo(71));
    assertThat(secondChange.getProperties(), hasKey("something"));
    assertThat(secondChange.getProperties().get("something"), equalTo("customValue"));
  }
}
