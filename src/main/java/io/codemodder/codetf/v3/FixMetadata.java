package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/** Metadata about a fix. */
public final class FixMetadata {
  private final String id;
  private final String summary;
  private final String description;
  private final List<Reference> references;
  private final GenerationMetadata generation;

  @JsonCreator
  public FixMetadata(
      @JsonProperty("id") String id,
      @JsonProperty("summary") String summary,
      @JsonProperty("description") String description,
      @JsonProperty("references") List<Reference> references,
      @JsonProperty("generation") GenerationMetadata generation) {
    this.id = Objects.requireNonNull(id, "id cannot be null");
    this.summary = Objects.requireNonNull(summary, "summary cannot be null");
    this.description = Objects.requireNonNull(description, "description cannot be null");
    this.references = Objects.requireNonNull(references, "references cannot be null");
    this.generation = Objects.requireNonNull(generation, "generation cannot be null");
  }

  public String getId() {
    return id;
  }

  public String getSummary() {
    return summary;
  }

  public String getDescription() {
    return description;
  }

  public List<Reference> getReferences() {
    return references;
  }

  public GenerationMetadata getGeneration() {
    return generation;
  }
}
