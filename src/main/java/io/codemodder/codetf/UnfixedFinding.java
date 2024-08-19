package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes an unfixed finding. */
public final class UnfixedFinding extends Finding {

  private final String path;
  private final Integer line;
  private final String reason;

  @JsonCreator
  public UnfixedFinding(
      @JsonProperty(value = "id", index = 1) final String id,
      @JsonProperty(value = "rule", index = 2) final DetectorRule rule,
      @JsonProperty(value = "path", index = 3) final String path,
      @JsonProperty(value = "line", index = 4) final Integer line,
      @JsonProperty(value = "reason", index = 5) final String reason) {
    super(id, rule);
    this.path = CodeTFValidator.requireNonBlank(path);
    this.line = line;
    this.reason = CodeTFValidator.requireNonBlank(reason);
  }

  public UnfixedFinding(
      @JsonProperty(value = "rule", index = 2) final DetectorRule rule,
      @JsonProperty(value = "path", index = 3) final String path,
      @JsonProperty(value = "line", index = 4) final Integer line,
      @JsonProperty(value = "reason", index = 5) final String reason) {
    this(null, rule, path, line, reason);
  }

  public String getPath() {
    return path;
  }

  public String getReason() {
    return reason;
  }

  public Integer getLine() {
    return line;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof final UnfixedFinding that)) return false;
    if (!super.equals(o)) return false;

    return path.equals(that.path) && Objects.equals(line, that.line) && reason.equals(that.reason);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + path.hashCode();
    result = 31 * result + Objects.hashCode(line);
    result = 31 * result + reason.hashCode();
    return result;
  }
}
