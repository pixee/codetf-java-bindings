package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CodeTFDiffSide {
  @JsonProperty("left")
  LEFT,
  @JsonProperty("right")
  RIGHT,
}
