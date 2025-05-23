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
    CodeTFChangesetEntry entry =
        new CodeTFChangesetEntry("src/foo", "diff", List.of(), ai, null, false, null, null);
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
  void it_has_changeset_with_fix_quality_metadata() {
    CodeTFAiMetadata ai = new CodeTFAiMetadata("ai", "best-model-ever", null);

    FixQuality fixQuality =
        new FixQuality(
            new FixRating("its safe", 100),
            new FixRating("its effective", 99),
            new FixRating("its clean", 98));
    CodeTFChangesetEntry entry =
        new CodeTFChangesetEntry(
            "src/foo", "diff", List.of(), ai, Strategy.HYBRID, true, null, fixQuality);

    assertEquals(entry.getStrategy(), Strategy.HYBRID);

    assertEquals(entry.getFixQuality(), fixQuality);
    assertEquals(fixQuality.getSafetyRating().score(), 100);
    assertEquals(fixQuality.getEffectivenessRating().score(), 99);
    assertEquals(fixQuality.getCleanlinessRating().score(), 98);

    assertTrue(entry.isProvisional());
  }

  @Test
  void it_has_failure_state() {
    Failure state = new Failure("reason", "exception");
    assertEquals("reason", state.getReason());
    assertEquals("exception", state.getException());
  }

  @Test
  void it_has_changeset_with_change_with_fixed_finding() {
    DetectorRule rule = new DetectorRule("rule", "Here's a rule", null);
    FixedFinding finding = new FixedFinding("finding", rule);
    CodeTFChange change =
        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of(finding));
    CodeTFChangesetEntry entry = new CodeTFChangesetEntry("src/foo", "diff", List.of(change));
    assertEquals(1, entry.getFixedFindings().size());

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
    assertEquals(1, result.getFixedFindings().size());
  }

  @Test
  void it_has_changeset_with_fixed_finding() {
    DetectorRule rule = new DetectorRule("rule", "Here's a rule", null);
    FixedFinding finding = new FixedFinding("finding", rule);
    CodeTFChange change =
        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, null);
    CodeTFChangesetEntry entry =
        new CodeTFChangesetEntry(
            "src/foo", "diff", List.of(change), null, null, false, List.of(finding), null);
    assertEquals(1, entry.getFixedFindings().size());

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
    assertEquals(1, result.getFixedFindings().size());
  }

  @Test
  void it_has_multiple_changes_with_findings_and_changeset_with_findings() {
    DetectorRule rule = new DetectorRule("rule", "Here's a rule", null);
    FixedFinding finding = new FixedFinding("finding", rule);
    CodeTFChange change =
        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of(finding));
    CodeTFChange change2 =
        new CodeTFChange(1, null, "whatever", CodeTFDiffSide.RIGHT, null, null, List.of(finding));
    CodeTFChangesetEntry entry =
        new CodeTFChangesetEntry(
            "src/foo", "diff", List.of(change, change2), null, null, false, List.of(finding), null);
    assertEquals(3, entry.getFixedFindings().size());

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
            List.of(entry, entry),
            List.of());
    assertEquals(6, result.getFixedFindings().size());
  }
}
