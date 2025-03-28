package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A rating for a fix quality dimension. */
public final class Rating {
  private final int score;
  private final String description;

  @JsonCreator
  public Rating(@JsonProperty("score") int score, @JsonProperty("description") String description) {
    this.score = score;
    this.description = description;
  }

  public int getScore() {
    return score;
  }

  public String getDescription() {
    return description;
  }
}
