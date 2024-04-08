package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes an unfixed finding. */
public final class UnfixedFinding {

  private final String id;
  private final DetectorRule rule;
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
    this.id = CodeTFValidator.requireNonBlank(id);
    this.rule = Objects.requireNonNull(rule);
    this.path = CodeTFValidator.requireNonBlank(path);
    this.line = line;
    this.reason = CodeTFValidator.requireNonBlank(reason);
  }

  public String getId() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public String getReason() {
    return reason;
  }

  public DetectorRule getRule() {
    return rule;
  }

  public Integer getLine() {
    return line;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UnfixedFinding that = (UnfixedFinding) o;
    return Objects.equals(id, that.id)
        && Objects.equals(rule, that.rule)
        && Objects.equals(path, that.path)
        && Objects.equals(line, that.line)
        && Objects.equals(reason, that.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rule, path, line, reason);
  }
}
