package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonValue;

/** Side of a diff where a change is located. */
public enum DiffSide {
  LEFT("left"),
  RIGHT("right");

  private final String value;

  DiffSide(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
