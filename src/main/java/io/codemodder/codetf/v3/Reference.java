package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** A reference to additional information. */
public final class Reference {
  private final String url;
  private final String description;

  @JsonCreator
  public Reference(
      @JsonProperty("url") String url, @JsonProperty("description") String description) {
    this.url = Objects.requireNonNull(url, "url cannot be null");
    this.description = description != null ? description : url;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }
}
