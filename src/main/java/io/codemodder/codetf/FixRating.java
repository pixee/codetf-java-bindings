package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes a constituent factor of {@link FixQuality}. */
public final class FixRating {

  private final String description;
  private final int score;

  /**
   * Creates a new {@link FixRating}.
   *
   * @param description the description of the rating
   * @param score the score of the rating
   */
  @JsonCreator
  public FixRating(
      @JsonProperty("description") final String description,
      @JsonProperty("score") final int score) {
    this.description = Objects.requireNonNull(description);

    if (score < 0 || score > 100) {
      throw new IllegalArgumentException("score must be between 0 and 100");
    }
    this.score = score;
  }

  public String description() {
    return description;
  }

  public int score() {
    return score;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (FixRating) obj;
    return Objects.equals(this.description, that.description) && this.score == that.score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, score);
  }

  @Override
  public String toString() {
    return "FixRating[" + "description=" + description + ", " + "score=" + score + ']';
  }
}
