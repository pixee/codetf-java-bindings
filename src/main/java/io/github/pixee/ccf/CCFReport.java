package io.github.pixee.ccf;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/**
 * Top level CCF reporting object. This is the root object to be deserialized from a CCF input
 * stream.
 */
public final class CCFReport {

  private final CCFRun run;

  private final List<CCFResult> results;

  @JsonCreator
  public CCFReport(
      @JsonProperty("run") final CCFRun run,
      @JsonProperty("results") final List<CCFResult> results) {
    this.run = Objects.requireNonNull(run, "run");
    this.results = CCFValidator.toImmutableCopyOrEmptyOnNull(results);
  }

  public CCFRun getRun() {
    return run;
  }

  public List<CCFResult> getResults() {
    return results;
  }
}
