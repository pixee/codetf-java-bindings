package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Quality ratings for a fix. */
public final class FixQuality {
  private final Rating safetyRating;
  private final Rating effectivenessRating;
  private final Rating cleanlinessRating;

  @JsonCreator
  public FixQuality(
      @JsonProperty("safetyRating") Rating safetyRating,
      @JsonProperty("effectivenessRating") Rating effectivenessRating,
      @JsonProperty("cleanlinessRating") Rating cleanlinessRating) {
    this.safetyRating = Objects.requireNonNull(safetyRating, "safetyRating cannot be null");
    this.effectivenessRating =
        Objects.requireNonNull(effectivenessRating, "effectivenessRating cannot be null");
    this.cleanlinessRating =
        Objects.requireNonNull(cleanlinessRating, "cleanlinessRating cannot be null");
  }

  public Rating getSafetyRating() {
    return safetyRating;
  }

  public Rating getEffectivenessRating() {
    return effectivenessRating;
  }

  public Rating getCleanlinessRating() {
    return cleanlinessRating;
  }
}
