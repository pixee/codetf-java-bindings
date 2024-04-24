package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeTFAiMetadata {

  private final String provider;
  private final String model;
  private final int tokens;

  @JsonCreator
  public CodeTFAiMetadata(
      @JsonProperty("provider") final String provider,
      @JsonProperty("model") final String model,
      @JsonProperty("tokens") final Integer tokens) {
    this.provider = provider;
    this.model = model;
    this.tokens = tokens == null ? 0 : tokens;
  }

  public String getProvider() {
    return provider;
  }

  public String getModel() {
    return model;
  }

  public int getTokens() {
    return tokens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CodeTFAiMetadata that = (CodeTFAiMetadata) o;
    return tokens == that.tokens && provider.equals(that.provider) && model.equals(that.model);
  }
}
