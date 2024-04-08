package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** Describes the "rule" section of a detection tool. */
public final class DetectorRule {

  private final String id;
  private final String name;
  private final String url;

  @JsonCreator
  public DetectorRule(
      @JsonProperty(value = "id", index = 1) String id,
      @JsonProperty(value = "name", index = 2) String name,
      @JsonProperty(value = "url", index = 3) String url) {
    this.id = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DetectorRule rule = (DetectorRule) o;
    return Objects.equals(id, rule.id)
        && Objects.equals(name, rule.name)
        && Objects.equals(url, rule.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, url);
  }
}
