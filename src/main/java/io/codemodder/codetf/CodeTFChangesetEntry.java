package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/** Describes an individual changeset entry. */
public final class CodeTFChangesetEntry {

  private final String path;

  private final String diff;

  private final List<CodeTFChange> changes;

  private final CodeTFAiMetadata ai;

  @JsonCreator
  public CodeTFChangesetEntry(
      @JsonProperty("path") final String path,
      @JsonProperty("diff") final String diff,
      @JsonProperty("changes") final List<CodeTFChange> changes,
      @JsonProperty("ai") final CodeTFAiMetadata ai) {
    this.path = CodeTFValidator.requireRelativePath(path);
    this.diff = CodeTFValidator.requireNonBlank(diff);
    this.changes = CodeTFValidator.toImmutableCopyOrEmptyOnNull(changes);
    this.ai = ai;
  }

  public CodeTFChangesetEntry(
      final String path, final String diff, final List<CodeTFChange> changes) {
    this(path, diff, changes, null);
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CodeTFChangesetEntry entry = (CodeTFChangesetEntry) o;
    return Objects.equals(path, entry.path)
        && Objects.equals(diff, entry.diff)
        && Objects.equals(changes, entry.changes)
        && Objects.equals(ai, entry.ai);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, diff, changes, ai);
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
