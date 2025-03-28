package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Metadata describing fix outcome. */
public final class FixStatus {
  private final FixStatusType status;
  private final String reason;
  private final String details;

  @JsonCreator
  public FixStatus(
      @JsonProperty("status") FixStatusType status,
      @JsonProperty("reason") String reason,
      @JsonProperty("details") String details) {
    this.status = status;
    this.reason = reason;
    this.details = details;
  }

  public FixStatusType getStatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }

  public String getDetails() {
    return details;
  }
}
