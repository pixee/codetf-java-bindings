package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a fix result. */
public enum FixStatusType {
  FIXED("fixed"),
  SKIPPED("skipped"),
  FAILED("failed"),
  WONTFIX("wontfix");

  private final String value;

  FixStatusType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
