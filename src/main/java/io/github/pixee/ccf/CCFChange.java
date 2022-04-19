package io.github.pixee.ccf;

import static io.github.pixee.ccf.CCFValidator.toImmutableCopyOrEmptyOnNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Describes a "change" in a CCF report. */
public final class CCFChange {

  private final int lineNumber;

  private final String category;

  private final String description;

  private final Map<String, String> properties;

  @JsonCreator
  public CCFChange(
      @JsonProperty("lineNumber") final int lineNumber,
      @JsonProperty("properties") final Map<String, String> properties,
      @JsonProperty("category") final String category,
      @JsonProperty("description") final String description) {

    if (lineNumber < 1) {
      throw new IllegalArgumentException("line number must be positive");
    }

    this.lineNumber = lineNumber;
    this.properties = CCFValidator.toImmutableCopyOrEmptyOnNull(properties);
    this.category = CCFValidator.requireNonBlank(category);
    this.description = description;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public String getDescription() {
    return description;
  }

  public String getCategory() {
    return category;
  }

  public int getLineNumber() {
    return lineNumber;
  }
}
