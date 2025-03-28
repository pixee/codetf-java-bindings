package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/** A set of changes for a single file. */
public final class ChangeSet {
  private final String path;
  private final String diff;
  private final List<Change> changes;

  @JsonCreator
  public ChangeSet(
      @JsonProperty("path") String path,
      @JsonProperty("diff") String diff,
      @JsonProperty("changes") List<Change> changes) {
    this.path = Objects.requireNonNull(path, "path cannot be null");
    this.diff = Objects.requireNonNull(diff, "diff cannot be null");
    this.changes = Objects.requireNonNull(changes, "changes cannot be null");
  }

  public String getPath() {
    return path;
  }

  public String getDiff() {
    return diff;
  }

  public List<Change> getChanges() {
    return changes;
  }
}
