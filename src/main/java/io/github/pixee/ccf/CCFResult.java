package io.github.pixee.ccf;

import static io.github.pixee.ccf.CCFValidator.toImmutableCopyOrEmptyOnNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Describes an individual change result. */
public final class CCFResult {

  private final String path;

  private final String diff;

  private final List<CCFChange> changes;

  @JsonCreator
  public CCFResult(
      @JsonProperty("path") final String path,
      @JsonProperty("diff") final String diff,
      @JsonProperty("changes") final List<CCFChange> changes) {
    this.path = CCFValidator.requireNonBlank(path);
    this.diff = CCFValidator.requireNonBlank(diff);
    this.changes = CCFValidator.toImmutableCopyOrEmptyOnNull(changes);
  }

  public String getPath() {
    return path;
  }

  public String getDiff() {
    return diff;
  }

  public List<CCFChange> getChanges() {
    return changes;
  }
}
