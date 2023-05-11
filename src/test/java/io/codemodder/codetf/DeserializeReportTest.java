package io.codemodder.codetf;

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
    String inputJson = "{  \"artifact\" : \"/path/to/semmle.sarif\" , \"sha1\" : \"154F\" }";
    CodeTFSarifInput input = mapper.readValue(inputJson, CodeTFSarifInput.class);
    assertThat(input.getArtifact(), equalTo("/path/to/semmle.sarif"));
    assertThat(input.getSha1(), equalTo("154F"));
  }

  @Test
  void it_deserializes_basic_report() throws IOException {
    File file = new File("src/test/resources/basic.codetf");
    CodeTFReport report = mapper.readValue(file, CodeTFReport.class);

    CodeTFRun run = report.getRun();
    assertThat(run.getCommandLine(), equalTo("pixee change . --dry-run"));
    assertThat(run.getElapsed(), equalTo(105024L));
    assertThat(run.getTool(), equalTo("pixee-tool"));
    assertThat(run.getVendor(), equalTo("pixee"));

    List<CodeTFResult> results = report.getResults();
    assertThat(results.size(), equalTo(1));
    CodeTFResult result = results.get(0);
    assertThat(result.getCodemod(), equalTo("codemodder:java/deserialization"));
    assertThat(
        result.getSummary(), equalTo("Hardened object deserialization calls against attack"));
    assertThat(
        result.getDescription(),
        equalTo("Lengthier description about deserialization risks, protections, etc..."));
    assertThat(
        result.getReferences(),
        contains(
            new CodeTFReference(
                "https://www.oracle.com/technetwork/java/seccodeguide-139067.html#8",
                "Oracle's Secure Coding Guidelines for Java SE")));

    assertThat(result.getProperties().size(), equalTo(0));
    assertThat(result.getFailedFiles(), contains("/foo/failed.java"));

    List<CodeTFChangesetEntry> changeset = result.getChangeset();
    assertThat(
        changeset.get(0).getPath(), equalTo("src/main/java/org/acme/MyDeserializerAction.java"));
    assertThat(changeset.get(0).getDiff(), equalTo("... udiff text..."));

    assertThat(changeset.get(1).getPath(), equalTo("pom.xml"));
    assertThat(changeset.get(1).getDiff(), equalTo("... pom udiff text..."));
    assertThat(changeset.get(1).getChanges().size(), equalTo(1));
    assertThat(changeset.get(1).getChanges().get(0).getLineNumber(), equalTo(155));
    assertThat(
        changeset.get(1).getChanges().get(0).getDescription(),
        equalTo("Added java-security-toolkit for MyDeserializationAction.java"));
  }
}
