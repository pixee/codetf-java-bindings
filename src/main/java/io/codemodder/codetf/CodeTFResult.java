package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.util.stream.Collectors;

/** Describes the "result" section of a CodeTF document. */
public final class CodeTFResult {

  private final String codemod;
  private final String summary;
  private final String description;
  private final DetectionTool detectionTool;
  private final Set<String> failedFiles;
  private final List<CodeTFReference> references;
  private final Map<String, String> properties;
  private final List<CodeTFChangesetEntry> changeset;
  private final List<UnfixedFinding> unfixedFindings;

  @JsonCreator
  public CodeTFResult(
      @JsonProperty(value = "codemod", index = 1) final String codemod,
      @JsonProperty(value = "summary", index = 2) final String summary,
      @JsonProperty(value = "description", index = 3) final String description,
      @JsonProperty(value = "detectionTool", index = 4) final DetectionTool detectionTool,
      @JsonProperty(value = "failedFiles", index = 5) final Set<String> failedFiles,
      @JsonProperty(value = "references", index = 6) final List<CodeTFReference> references,
      @JsonProperty(value = "properties", index = 7) final Map<String, String> properties,
      @JsonProperty(value = "changeset", index = 8) final List<CodeTFChangesetEntry> changeset,
      @JsonProperty(value = "unfixed", index = 9) final List<UnfixedFinding> unfixedFindings) {
    this.codemod = CodeTFValidator.requireNonBlank(codemod);
    this.summary = CodeTFValidator.requireNonBlank(summary);
    this.description = CodeTFValidator.requireNonBlank(description);
    this.detectionTool = detectionTool;
    this.failedFiles = CodeTFValidator.toImmutableCopyOrEmptyOnNull(failedFiles);
    this.references = CodeTFValidator.toImmutableCopyOrEmptyOnNull(references);
    this.properties = CodeTFValidator.toImmutableCopyOrEmptyOnNull(properties);
    this.changeset = Objects.requireNonNull(changeset);
    this.unfixedFindings = CodeTFValidator.toImmutableCopyOrEmptyOnNull(unfixedFindings);
  }

  /** Constructor for required (non-nullable) fields */
  public CodeTFResult(
      final String codemod,
      final String summary,
      final String description,
      final List<CodeTFChangesetEntry> changeset) {
    this(codemod, summary, description, null, null, null, null, changeset, null);
  }

  public String getCodemod() {
    return codemod;
  }

  public String getSummary() {
    return summary;
  }

  public String getDescription() {
    return description;
  }

  public DetectionTool getDetectionTool() {
    return detectionTool;
  }

  public Set<String> getFailedFiles() {
    return failedFiles;
  }

  public List<CodeTFReference> getReferences() {
    return references;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public List<CodeTFChangesetEntry> getChangeset() {
    return changeset;
  }

  public List<UnfixedFinding> getUnfixedFindings() {
    return unfixedFindings;
  }

  public boolean usesAi() {
    return changeset.stream().anyMatch(CodeTFChangesetEntry::usesAi);
  }

  public List<FixedFinding> getFixedFindings() {
    return changeset.stream()
        .map(CodeTFChangesetEntry::getChanges)
        .flatMap(List::stream)
        .map(CodeTFChange::getFixedFindings)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  /** Create a new CodeTFResult builder based on an existing instance. */
  public static Builder basedOn(final CodeTFResult result) {
    return new Builder(result);
  }

  /** Builder for CodeTFResult which was based on an existing instance. */
  public static class Builder {
    private final CodeTFResult originalResult;
    private String updatedSummary;
    private String updatedDescription;
    private List<CodeTFReference> updatedReferences;

    private Builder(final CodeTFResult result) {
      this.originalResult = Objects.requireNonNull(result);
    }

    /** Update the CodeTFResult with the given summary. */
    public Builder withSummary(final String summary) {
      Objects.requireNonNull(summary);
      this.updatedSummary = summary;
      return this;
    }

    /** Update the CodeTFResult with the given description. */
    public Builder withDescription(final String description) {
      Objects.requireNonNull(description);
      this.updatedDescription = description;
      return this;
    }

    public Builder withReferences(final List<CodeTFReference> references) {
      Objects.requireNonNull(references);
      this.updatedReferences = references;
      return this;
    }

    /** Update the CodeTFResult with additional references. */
    public Builder withAdditionalReferences(final List<CodeTFReference> references) {
      Objects.requireNonNull(references);
      if (updatedReferences == null) {
        updatedReferences = new ArrayList<>(originalResult.references);
      }
      updatedReferences.addAll(references);
      return this;
    }

    public Builder withDetectionTool(final DetectionTool detectionTool) {
      Objects.requireNonNull(detectionTool);
      return this;
    }

    public Builder withChangeset(final List<CodeTFChangesetEntry> changeset) {
      Objects.requireNonNull(changeset);
      return this;
    }

    public Builder withUnfixedFindings(final List<UnfixedFinding> unfixedFindings) {
      Objects.requireNonNull(unfixedFindings);
      return this;
    }

    public CodeTFResult build() {
      return new CodeTFResult(
          originalResult.getCodemod(),
          updatedSummary != null ? updatedSummary : originalResult.getSummary(),
          updatedDescription != null ? updatedDescription : originalResult.getDescription(),
          originalResult.detectionTool,
          originalResult.getFailedFiles(),
          updatedReferences != null ? updatedReferences : originalResult.getReferences(),
          originalResult.getProperties(),
          originalResult.getChangeset(),
          originalResult.unfixedFindings);
    }
  }
}
