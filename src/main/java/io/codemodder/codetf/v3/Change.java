package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/** A single change in a changeset. */
public final class Change {
  private final int lineNumber;
  private final String description;
  private final DiffSide diffSide;
  private final Map<String, Object> properties;
  private final List<PackageAction> packageActions;

  @JsonCreator
  public Change(
      @JsonProperty("lineNumber") int lineNumber,
      @JsonProperty("description") String description,
      @JsonProperty("diffSide") DiffSide diffSide,
      @JsonProperty("properties") Map<String, Object> properties,
      @JsonProperty("packageActions") List<PackageAction> packageActions) {
    if (lineNumber < 1) {
      throw new IllegalArgumentException("lineNumber must be greater than 0");
    }
    if (description != null && description.isEmpty()) {
      throw new IllegalArgumentException("description must not be empty");
    }
    this.lineNumber = lineNumber;
    this.description = description;
    this.diffSide = diffSide != null ? diffSide : DiffSide.RIGHT;
    this.properties = properties;
    this.packageActions = packageActions;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String getDescription() {
    return description;
  }

  public DiffSide getDiffSide() {
    return diffSide;
  }

  public Map<String, Object> getProperties() {
    return properties;
  }

  public List<PackageAction> getPackageActions() {
    return packageActions;
  }
}
