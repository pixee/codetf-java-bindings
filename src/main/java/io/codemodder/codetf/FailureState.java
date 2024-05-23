package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FailureState {
  private final boolean failed;
  private final String reason;
  private final String exception;

  @JsonCreator
  public FailureState(
      @JsonProperty("failed") final boolean failed,
      @JsonProperty("reason") final String reason,
      @JsonProperty("exception") final String exception) {
    this.failed = failed;
    this.reason = reason;
    this.exception = exception;
  }

  public boolean failed() {
    return failed;
  }

  public String reason() {
    return reason;
  }

  public String exception() {
    return exception;
  }
}
