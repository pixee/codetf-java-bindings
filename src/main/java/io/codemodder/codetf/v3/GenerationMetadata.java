package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Metadata about how a fix was generated. */
public final class GenerationMetadata {
  private final Strategy strategy;
  private final AIMetadata ai;
  private final boolean provisional;

  @JsonCreator
  public GenerationMetadata(
      @JsonProperty("strategy") Strategy strategy,
      @JsonProperty("ai") AIMetadata ai,
      @JsonProperty("provisional") boolean provisional) {
    this.strategy = Objects.requireNonNull(strategy, "strategy cannot be null");
    this.ai = ai;
    this.provisional = provisional;
  }

  public Strategy getStrategy() {
    return strategy;
  }

  public AIMetadata getAi() {
    return ai;
  }

  public boolean isProvisional() {
    return provisional;
  }
}
