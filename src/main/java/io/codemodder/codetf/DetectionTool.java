package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/** Metadata about the external tool that generated results consumed by this codemod */
public final class DetectionTool {
  private final String name;
  private final DetectorRule rule;
  private final List<DetectorFinding> findings;

  @JsonCreator
  public DetectionTool(
      @JsonProperty(value = "name", index = 1) String name,
      @JsonProperty(value = "rule", index = 2) DetectorRule rule,
      @JsonProperty(value = "findings", index = 3) List<DetectorFinding> findings) {
    this.name = Objects.requireNonNull(name);
    this.rule = Objects.requireNonNull(rule);
    this.findings = CodeTFValidator.toImmutableCopyOrEmptyOnNull(findings);
  }

  public String getName() {
    return name;
  }

  public DetectorRule getRule() {
    return rule;
  }

  public List<DetectorFinding> getFindings() {
    return findings;
  }
}
