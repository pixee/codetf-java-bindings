package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** A detection rule. */
public final class Rule {
  private final String id;
  private final String name;
  private final String url;

  @JsonCreator
  public Rule(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("url") String url) {
    this.id = Objects.requireNonNull(id, "id cannot be null");
    this.name = Objects.requireNonNull(name, "name cannot be null");
    this.url = url;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }
}
