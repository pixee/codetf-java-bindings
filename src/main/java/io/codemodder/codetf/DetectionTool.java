package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Metadata about the external tool that generated results consumed by this codemod */
public final class DetectionTool {

  private final String name;

  @JsonCreator
  public DetectionTool(@JsonProperty(value = "name", index = 1) String name) {
    this.name = Objects.requireNonNull(name);
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DetectionTool that = (DetectionTool) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
