package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Strategy {
  @JsonProperty("ai")
  AI,
  @JsonProperty("hybrid")
  HYBRID,
  @JsonProperty("deterministic")
  DETERMINISTIC,
}
