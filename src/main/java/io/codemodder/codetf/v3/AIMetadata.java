package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Metadata about AI usage. */
public final class AIMetadata {
  private final String provider;
  private final List<String> models;
  private final Integer totalTokens;
  private final Integer completionTokens;
  private final Integer promptTokens;

  @JsonCreator
  public AIMetadata(
      @JsonProperty("provider") String provider,
      @JsonProperty("models") List<String> models,
      @JsonProperty("total_tokens") Integer totalTokens,
      @JsonProperty("completion_tokens") Integer completionTokens,
      @JsonProperty("prompt_tokens") Integer promptTokens) {
    this.provider = provider;
    this.models = models;
    this.totalTokens = totalTokens;
    this.completionTokens = completionTokens;
    this.promptTokens = promptTokens;
  }

  public String getProvider() {
    return provider;
  }

  public List<String> getModels() {
    return models;
  }

  @JsonProperty("total_tokens")
  public Integer getTotalTokens() {
    return totalTokens;
  }

  @JsonProperty("completion_tokens")
  public Integer getCompletionTokens() {
    return completionTokens;
  }

  @JsonProperty("prompt_tokens")
  public Integer getPromptTokens() {
    return promptTokens;
  }
}
