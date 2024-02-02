package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Describes a "change" in a report. */
public final class CodeTFChange {

  private final int lineNumber;

  private final String description;
  private final CodeTFDiffSide diffSide;

  private final Map<String, String> properties;

  private final List<CodeTFPackageAction> packageActions;

  private final List<CodeTFParameter> parameters;

  @JsonCreator
  public CodeTFChange(
      @JsonProperty("lineNumber") final int lineNumber,
      @JsonProperty("properties") final Map<String, String> properties,
      @JsonProperty("description") final String description,
      @JsonProperty("diffSide") final CodeTFDiffSide diffSide,
      @JsonProperty("packageActions") final List<CodeTFPackageAction> packageActions,
      @JsonProperty("parameters") final List<CodeTFParameter> parameters) {

    if (lineNumber < 1) {
      throw new IllegalArgumentException("line number must be positive");
    }

    this.lineNumber = lineNumber;
    this.properties = CodeTFValidator.toImmutableCopyOrEmptyOnNull(properties);
    this.packageActions = CodeTFValidator.toImmutableCopyOrEmptyOnNull(packageActions);
    this.description = CodeTFValidator.optionalString(description);
    this.diffSide = diffSide;
    this.parameters = parameters;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public String getDescription() {
    return description;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public CodeTFDiffSide getDiffSide() {
    return diffSide;
  }

  public List<CodeTFPackageAction> getPackageActions() {
    return packageActions;
  }

  public List<CodeTFParameter> getParameters() {
    return parameters;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final CodeTFChange that = (CodeTFChange) o;
    return lineNumber == that.lineNumber
        && Objects.equals(description, that.description)
        && Objects.equals(properties, that.properties)
        && Objects.equals(parameters, that.parameters)
        && Objects.equals(diffSide, that.diffSide);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lineNumber, description, properties, parameters);
  }

  @Override
  public String toString() {
    return "CodeTFChange{"
        + "lineNumber="
        + lineNumber
        + ", description='"
        + description
        + '\''
        + ", diffSide="
        + diffSide
        + ", properties="
        + properties
        + ", parameters="
        + parameters
        + '}';
  }

  /** Create a new {@link CodeTFChange} builder based on an existing instance. */
  public static Builder basedOn(final CodeTFChange change) {
    return new Builder(change);
  }

  /** Builder for {@link CodeTFChange} which was based on an existing instance. */
  public static class Builder {
    private final CodeTFChange originalChange;
    private String updatedDescription;
    private Map<String, String> updatedProperties;

    private Builder(final CodeTFChange change) {
      this.originalChange = Objects.requireNonNull(change);
      this.updatedProperties = new HashMap<>(change.getProperties());
    }

    /** Update the {@link CodeTFChange} with the given description. */
    public Builder withDescription(final String description) {
      Objects.requireNonNull(description);
      this.updatedDescription = description;
      return this;
    }

    /** Update the {@link CodeTFChange} with additional properties. */
    public Builder withAdditionalProperties(final Map<String, String> newProperties) {
      Objects.requireNonNull(newProperties);
      if (this.updatedProperties == null) {
        this.updatedProperties = new HashMap<>(originalChange.properties);
      }
      this.updatedProperties.putAll(newProperties);
      return this;
    }

    public CodeTFChange build() {
      return new CodeTFChange(
          originalChange.getLineNumber(),
          updatedProperties != null ? updatedProperties : originalChange.getProperties(),
          updatedDescription != null ? updatedDescription : originalChange.getDescription(),
          originalChange.getDiffSide(),
          originalChange.getPackageActions(),
          originalChange.getParameters());
    }
  }
}
