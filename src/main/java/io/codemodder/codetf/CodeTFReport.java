package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/** Top level reporting object. This is the root object to be deserialized from an input stream. */
public final class CodeTFReport {

  private final CodeTFRun run;

  private final List<CodeTFResult> results;

  @JsonCreator
  public CodeTFReport(
      @JsonProperty("run") final CodeTFRun run,
      @JsonProperty("results") final List<CodeTFResult> results) {
    this.run = Objects.requireNonNull(run, "run");
    this.results = CodeTFValidator.toImmutableCopyOrEmptyOnNull(results);
  }

  public CodeTFRun getRun() {
    return run;
  }

  /**
   * Return a {@link List} of individual codemod's results. If this list is not empty, that does not
   * mean there are changes to make.
   */
  public List<CodeTFResult> getResults() {
    return results;
  }

  /** Returns true if the CodeTF describes any changes made to the code. */
  public boolean hasCodeChanges() {
    return results.stream()
        .anyMatch(result -> result.getChangeset() != null && !result.getChangeset().isEmpty());
  }
}
