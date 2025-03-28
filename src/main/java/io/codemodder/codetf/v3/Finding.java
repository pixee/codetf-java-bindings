package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** A finding from a detection tool. */
public final class Finding {
  private final String id;
  private final Rule rule;

  @JsonCreator
  public Finding(@JsonProperty("id") String id, @JsonProperty("rule") Rule rule) {
    this.id = Objects.requireNonNull(id, "id cannot be null");
    this.rule = rule;
  }

  public String getId() {
    return id;
  }

  public Rule getRule() {
    return rule;
  }
}
