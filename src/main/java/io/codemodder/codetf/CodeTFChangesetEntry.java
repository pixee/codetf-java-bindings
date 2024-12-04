package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/** Describes an individual changeset entry. */
public final class CodeTFChangesetEntry {

  private final String path;

  private final String diff;

  private final List<CodeTFChange> changes;

  private final CodeTFAiMetadata ai;
  private final Strategy strategy;
  private final boolean provisional;

  private final List<FixedFinding> fixedFindings;

  @JsonCreator
  public CodeTFChangesetEntry(
      @JsonProperty("path") final String path,
      @JsonProperty("diff") final String diff,
      @JsonProperty("changes") final List<CodeTFChange> changes,
      @JsonProperty("ai") final CodeTFAiMetadata ai,
      @JsonProperty("strategy") final Strategy strategy,
      @JsonProperty("provisional") final boolean provisional,
      @JsonProperty("fixedFindings") final List<FixedFinding> fixedFindings) {
    this.path = CodeTFValidator.requireRelativePath(path);
    this.diff = CodeTFValidator.requireNonBlank(diff);
    this.changes = CodeTFValidator.toImmutableCopyOrEmptyOnNull(changes);
    this.ai = ai;
    this.strategy = strategy;
    this.provisional = provisional;
    this.fixedFindings = CodeTFValidator.toImmutableCopyOrEmptyOnNull(fixedFindings);
  }

  public CodeTFChangesetEntry(
      final String path, final String diff, final List<CodeTFChange> changes) {
    this(path, diff, changes, null, null, false, null);
  }

  public String getPath() {
    return path;
  }

  public String getDiff() {
    return diff;
  }

  public List<CodeTFChange> getChanges() {
    return changes;
  }

  public CodeTFAiMetadata getAi() {
    return ai;
  }

  public boolean usesAi() {
    return ai != null;
  }

  public Strategy getStrategy() {
    return strategy;
  }

  public boolean isProvisional() {
    return provisional;
  }

  /** Fixed findings that are not associated with any particular change */
  public List<FixedFinding> getFixedFindings() {
    return Stream.concat(
            fixedFindings.stream(),
            changes.stream().map(CodeTFChange::getFixedFindings).flatMap(List::stream))
        .toList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CodeTFChangesetEntry entry = (CodeTFChangesetEntry) o;
    return Objects.equals(path, entry.path)
        && Objects.equals(diff, entry.diff)
        && Objects.equals(changes, entry.changes)
        && Objects.equals(ai, entry.ai)
        && Objects.equals(strategy, entry.strategy)
        && Objects.equals(provisional, entry.provisional);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, diff, changes, ai, strategy, provisional);
  }

  @Override
  public String toString() {
    return "CodeTFChangesetEntry{"
        + "path='"
        + path
        + '\''
        + ", diff='"
        + diff
        + '\''
        + ", changes="
        + changes
        + '}';
  }
}
