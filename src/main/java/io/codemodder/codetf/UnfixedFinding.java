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
}
