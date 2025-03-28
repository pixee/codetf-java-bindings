package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonValue;

/** Strategy used to generate a fix. */
public enum Strategy {
  AI("ai"),
  HYBRID("hybrid"),
  DETERMINISTIC("deterministic");

  private final String value;

  Strategy(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
