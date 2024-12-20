package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

final class CodeTFChangeTest {

  @Test
  void it_raises_npe_on_null_diff_side() {
    assertThrows(
        NullPointerException.class,
        () -> new CodeTFChange(1, null, null, null, null, null, List.of()));
  }

  @Test
  void it_raises_iae_on_negative_line_number() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new CodeTFChange(-1, null, null, CodeTFDiffSide.LEFT, null, null, List.of()));
  }

  @Test
  void it_creates_change() {
    CodeTFChange change =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertNotNull(change);
    assertEquals(1, change.getLineNumber());
    assertEquals(CodeTFDiffSide.LEFT, change.getDiffSide());
  }

  @Test
  void it_creates_change_with_properties() {
    CodeTFChange change =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertNotNull(change);
    assertEquals(1, change.getLineNumber());
    assertEquals(CodeTFDiffSide.LEFT, change.getDiffSide());
    assertNotNull(change.getProperties());
  }

  @Test
  void equals_and_hash_code() {
    CodeTFChange change1 =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    CodeTFChange change2 =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertEquals(change1, change2);
    assertEquals(change1.hashCode(), change2.hashCode());
  }

  /**
   * Presently, there is a known issue where our Jackson databind annotations on {@link
   * CodeTFChange} allow for this type to be deserialized with either property name {@code findings}
   * or {@code fixedFindings}. The spec calls for {@code findings}. While we have not yet fixed this
   * issue, this test demonstrates that the deserialization works as expected and serves as a
   * regression test when we do fix the issue.
   */
  @Test
  void parse_json() throws JsonProcessingException {
    final CodeTFChange expected =
        new CodeTFChange(
            1,
            null,
            null,
            CodeTFDiffSide.LEFT,
            null,
            null,
            List.of(new FixedFinding("finding-id", new DetectorRule("java/xss", "XSS", null))));
    String json =
        """
      {
        "lineNumber": 1,
        "properties": null,
        "description": null,
        "diffSide": "left",
        "packageActions": null,
        "parameters": null,
        "fixedFindings": [{
          "id": "finding-id",
          "rule": {
            "id": "java/xss",
            "name": "XSS"
          }
        }]
      }
    """;
    final var mapper = new ObjectMapper();
    final var actual = mapper.readValue(json, CodeTFChange.class);
    assertEquals(expected, actual);
  }
}
