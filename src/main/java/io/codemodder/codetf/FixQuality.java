package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes the quality of a fix. */
public final class FixQuality {

  private final FixRating safetyRating;
  private final FixRating effectivenessRating;
  private final FixRating cleanlinessRating;

  @JsonCreator
  public FixQuality(
      @JsonProperty("safetyRating") final FixRating safetyRating,
      @JsonProperty("effectivenessRating") final FixRating effectivenessRating,
      @JsonProperty("cleanlinessRating") final FixRating cleanlinessRating) {
    this.safetyRating = Objects.requireNonNull(safetyRating);
    this.effectivenessRating = Objects.requireNonNull(effectivenessRating);
    this.cleanlinessRating = Objects.requireNonNull(cleanlinessRating);
  }

  public FixRating getSafetyRating() {
    return safetyRating;
  }

  public FixRating getEffectivenessRating() {
    return effectivenessRating;
  }

  public FixRating getCleanlinessRating() {
    return cleanlinessRating;
  }
}
