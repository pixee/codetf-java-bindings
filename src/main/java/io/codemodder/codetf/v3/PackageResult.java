package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonValue;

/** Result of a package action. */
public enum PackageResult {
  COMPLETED("completed"),
  FAILED("failed"),
  SKIPPED("skipped");

  private final String value;

  PackageResult(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
