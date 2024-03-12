package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CodeTFResultTest {

  @Test
  void it_creates_result() {
    CodeTFResult result =
        new CodeTFResult(
            "codemodder:java/deserialization",
            "Hardened object deserialization calls against attack",
            "Lengthier description about deserialization risks, protections, etc...",
            null,
            Set.of("/foo/failed.java"),
            List.of(
                new CodeTFReference(
                    "https://www.oracle.com/technetwork/java/seccodeguide-139067.html#8",
                    "Oracle's Secure Coding Guidelines for Java SE")),
            null,
            List.of(
                new CodeTFChangesetEntry(
                    "src/main/java/org/acme/MyDeserializerAction.java",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null))),
                new CodeTFChangesetEntry(
                    "pom.xml",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null)))));
    assertNotNull(result);
    assertEquals("codemodder:java/deserialization", result.getCodemod());
    assertEquals("Hardened object deserialization calls against attack", result.getSummary());
    assertEquals(
        "Lengthier description about deserialization risks, protections, etc...",
        result.getDescription());
    assertEquals(1, result.getReferences().size());
    assertEquals(1, result.getFailedFiles().size());
    assertEquals(2, result.getChangeset().size());
  }

  @Test
  void it_creates_result_with_detection_tool() {
    CodeTFResult result =
        new CodeTFResult(
            "codemodder:java/deserialization",
            "Hardened object deserialization calls against attack",
            "Lengthier description about deserialization risks, protections, etc...",
            new DetectionTool("tool", new DetectorRule("rule", "Here's a rule", null), List.of()),
            Set.of("/foo/failed.java"),
            List.of(
                new CodeTFReference(
                    "https://www.oracle.com/technetwork/java/seccodeguide-139067.html#8",
                    "Oracle's Secure Coding Guidelines for Java SE")),
            null,
            List.of(
                new CodeTFChangesetEntry(
                    "src/main/java/org/acme/MyDeserializerAction.java",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null))),
                new CodeTFChangesetEntry(
                    "pom.xml",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null)))));
    assertNotNull(result);
    assertEquals("codemodder:java/deserialization", result.getCodemod());
    assertEquals("Hardened object deserialization calls against attack", result.getSummary());
    assertEquals(
        "Lengthier description about deserialization risks, protections, etc...",
        result.getDescription());
    assertEquals(1, result.getReferences().size());
    assertEquals(1, result.getFailedFiles().size());
    assertEquals(2, result.getChangeset().size());
    assertNotNull(result.getDetectionTool());
  }

  @Test
  void it_creates_detection_tool_with_empty_url() {
    DetectionTool tool =
        new DetectionTool(
            "tool",
            new DetectorRule("rule", "Here's a rule", null),
            List.of(
                new DetectorFinding("finding", true, "It's fixed"),
                new DetectorFinding("finding", false, null)));
    assertNotNull(tool);
    assertEquals("tool", tool.getName());
    assertEquals("rule", tool.getRule().getId());
    assertEquals("Here's a rule", tool.getRule().getName());
    assertEquals(2, tool.getFindings().size());
    assertTrue(tool.getRule().getUrl().isEmpty());
  }

  @Test
  void it_creates_detection_tool_with_url() {
    DetectionTool tool =
        new DetectionTool(
            "tool",
            new DetectorRule("rule", "Here's a rule", "https://example.com"),
            List.of(
                new DetectorFinding("finding", true, "It's fixed"),
                new DetectorFinding("finding", false, null)));
    assertNotNull(tool);
    assertEquals("tool", tool.getName());
    assertEquals("rule", tool.getRule().getId());
    assertEquals("Here's a rule", tool.getRule().getName());
    assertEquals(2, tool.getFindings().size());
    assertTrue(tool.getRule().getUrl().isPresent());
    assertEquals("https://example.com", tool.getRule().getUrl().get());
  }

  @Test
  void it_creates_finding() {
    DetectorFinding finding = new DetectorFinding("finding", true, "It's fixed");
    assertNotNull(finding);
    assertEquals("finding", finding.getId());
    assertTrue(finding.getFixed());
    assertEquals("It's fixed", finding.getReason().get());
  }

  @Test
  void it_raises_iae_on_fixed_finding_with_null_reason() {
    assertThrows(IllegalArgumentException.class, () -> new DetectorFinding("finding", true, null));
  }

  @Test
  void it_creates_finding_with_null_reason() {
    DetectorFinding finding = new DetectorFinding("finding", false, null);
    assertNotNull(finding);
    assertEquals("finding", finding.getId());
    assertFalse(finding.getFixed());
    assertTrue(finding.getReason().isEmpty());
  }
}
