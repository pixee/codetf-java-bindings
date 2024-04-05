package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes a fixed finding. */
public final class FixedFinding {

  private final String id;
  private final DetectorRule rule;

  @JsonCreator
  public FixedFinding(
      @JsonProperty(value = "id", index = 1) final String id,
      @JsonProperty(value = "rule", index = 2) final DetectorRule rule) {
    this.id = CodeTFValidator.requireNonBlank(id);
    this.rule = Objects.requireNonNull(rule);
  }

  public String getId() {
    return id;
  }

  public DetectorRule getRule() {
    return rule;
  }
}
