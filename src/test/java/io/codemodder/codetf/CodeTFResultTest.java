package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

final class CodeTFResultTest {

  @Test
  void it_creates_result() {
    CodeTFResult result =
        new CodeTFResult(
            "codemodder:java/deserialization",
            "Hardened object deserialization calls against attack",
            "Lengthier description about deserialization risks, protections, etc...",
            null,
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
                        new CodeTFChange(
                            1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of()))),
                new CodeTFChangesetEntry(
                    "pom.xml",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(
                            1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of())))),
            List.of());
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

    DetectionTool tool = new DetectionTool("acme-scanner");
    DetectorRule rule = new DetectorRule("rule", "Here's a rule", null);
    UnfixedFinding unfixedFinding =
        new UnfixedFinding("rule-id-23", rule, "/foo/bar.java", 42, "It's not fixed");

    CodeTFResult result =
        new CodeTFResult(
            "codemodder:java/deserialization",
            "Hardened object deserialization calls against attack",
            "Lengthier description about deserialization risks, protections, etc...",
            tool,
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
                        new CodeTFChange(
                            1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of()))),
                new CodeTFChangesetEntry(
                    "pom.xml",
                    "... udiff text...",
                    List.of(
                        new CodeTFChange(
                            1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of())))),
            List.of(unfixedFinding));
    assertNotNull(result);
    assertEquals("codemodder:java/deserialization", result.getCodemod());
    assertEquals("Hardened object deserialization calls against attack", result.getSummary());
    assertEquals(
        "Lengthier description about deserialization risks, protections, etc...",
        result.getDescription());
    assertEquals(1, result.getReferences().size());
    assertEquals(1, result.getFailedFiles().size());
    assertEquals(2, result.getChangeset().size());
    assertEquals(tool, result.getDetectionTool());
    assertEquals(1, result.getUnfixedFindings().size());
    assertNotNull(result.getDetectionTool());
  }

  @Test
  void it_creates_detection_tool() {
    DetectorRule rule = new DetectorRule("rule", "Here's a rule", null);
    DetectionTool tool = new DetectionTool("tool");
    assertEquals("tool", tool.getName());
    assertEquals("rule", rule.getId());
    assertEquals("Here's a rule", rule.getName());
  }

  @Test
  void it_creates_finding() {
    DetectorRule rule = new DetectorRule("foo", "bar", "");
    FixedFinding finding = new FixedFinding("finding", rule);
    assertEquals("finding", finding.getId());
    assertThrows(NullPointerException.class, () -> new FixedFinding("finding", null));
    assertThrows(IllegalArgumentException.class, () -> new FixedFinding("", rule));
    assertThrows(IllegalArgumentException.class, () -> new FixedFinding(null, rule));
  }

  @Test
  void it_creates_unfixed_findings() {
    DetectorRule rule = new DetectorRule("foo", "bar", "");
    assertThrows(
        IllegalArgumentException.class,
        () -> new UnfixedFinding("finding", rule, "src/foo", 15, null));
    assertThrows(
        IllegalArgumentException.class,
        () -> new UnfixedFinding("finding", rule, "src/foo", 15, ""));
    assertThrows(
        IllegalArgumentException.class,
        () -> new UnfixedFinding("finding", rule, "", 15, "reason here"));
    assertThrows(
        IllegalArgumentException.class,
        () -> new UnfixedFinding("finding", rule, null, 15, "reason here"));
  }

  @Test
  void it_has_changeset_with_ai() {
    CodeTFAiMetadata ai = new CodeTFAiMetadata("ai", "best-model-ever", null);
    CodeTFChangesetEntry entry = new CodeTFChangesetEntry("src/foo", "diff", List.of(), ai);
    assertTrue(entry.usesAi());

    final var result =
        new CodeTFResult(
            "codemodder:java/deserialization",
            "Hardened object deserialization calls against attack",
            "Lengthier description about deserialization risks, protections, etc...",
            null,
            null,
            Set.of("/foo/failed.java"),
            List.of(
                new CodeTFReference(
                    "https://www.oracle.com/technetwork/java/seccodeguide-139067.html#8",
                    "Oracle's Secure Coding Guidelines for Java SE")),
            null,
            List.of(entry),
            List.of());

    assertTrue(result.usesAi());
  }

  @Test
  void it_has_failure_state() {
    FailureState state = new FailureState(true, "reason", "exception");
    assertTrue(state.failed());
    assertEquals("reason", state.reason());
    assertEquals("exception", state.exception());
  }
}
