package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Failure {
  private final String reason;
  private final String exception;

  @JsonCreator
  public Failure(
      @JsonProperty("reason") final String reason,
      @JsonProperty("exception") final String exception) {
    this.reason = reason;
    this.exception = exception;
  }

  public String getReason() {
    return reason;
  }

  public String getException() {
    return exception;
  }
}
