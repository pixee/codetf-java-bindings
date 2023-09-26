package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes a "parameter" for a change in a report. */
public final class CodeTFParameter {

  private final String question;
  private final String name;
  private final String type;
  private final String label;
  private final String defaultValue;

  @JsonCreator
  public CodeTFParameter(
      @JsonProperty("question") final String question,
      @JsonProperty("name") final String name,
      @JsonProperty("type") final String type,
      @JsonProperty("label") final String label,
      @JsonProperty("defaultValue") final String defaultValue) {
    this.question = Objects.requireNonNull(question);
    this.name = Objects.requireNonNull(name);
    this.type = CodeTFValidator.optionalString(type);
    this.label = CodeTFValidator.optionalString(label);
    this.defaultValue = Objects.requireNonNull(defaultValue);
  }

  public String getName() {
    return name;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public String getQuestion() {
    return question;
  }

  public String getLabel() {
    return label;
  }

  public String getType() {
    return type;
  }
}
