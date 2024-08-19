package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Describes a fixed finding. */
public final class FixedFinding extends Finding {

  @JsonCreator
  public FixedFinding(
      @JsonProperty(value = "id", index = 1) final String id,
      @JsonProperty(value = "rule", index = 2) final DetectorRule rule) {
    super(id, rule);
  }

  public FixedFinding(final DetectorRule rule) {
    super(rule);
  }

  @Override
  public boolean equals(final Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
