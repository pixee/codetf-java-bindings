package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonValue;

/** Package action type. */
public enum Action {
  ADD("add"),
  REMOVE("remove");

  private final String value;

  Action(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
