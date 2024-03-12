package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.Optional;

/** Describes an item in the "findings" section of a detection tool. */
public final class DetectorFinding {
  private final String id;
  private final Boolean fixed;
  private final Optional<String> reason;

  @JsonCreator
  public DetectorFinding(
      @JsonProperty(value = "id", index = 1) String id,
      @JsonProperty(value = "fixed", index = 2) Boolean fixed,
      @JsonProperty(value = "reason", index = 3) String reason) {
    this.id = Objects.requireNonNull(id);
    this.fixed = fixed;
    this.reason = Optional.ofNullable(reason);
    if (fixed && this.reason.isEmpty()) {
      throw new IllegalArgumentException("Fixed findings must have a reason");
    }
  }

  public String getId() {
    return id;
  }

  public Boolean getFixed() {
    return fixed;
  }

  public Optional<String> getReason() {
    return reason;
  }
}
