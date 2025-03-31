package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Top level reporting object for CodeTF v3. This is the root object to be deserialized from an
 * input stream.
 *
 * <p>Applications should deserialize this object using a centralized ObjectMapper:
 *
 * <pre>{@code
 * ObjectMapper mapper = new ObjectMapper();
 * // Configure the mapper as needed
 * CodeTF report = mapper.readValue(inputStream, CodeTF.class);
 * }</pre>
 */
public final class CodeTF {
  private final Run run;
  private final List<FixResult> results;

  @JsonCreator
  public CodeTF(@JsonProperty("run") Run run, @JsonProperty("results") List<FixResult> results) {
    this.run = Objects.requireNonNull(run, "run cannot be null");
    this.results =
        results != null ? Collections.unmodifiableList(results) : Collections.emptyList();
  }

  public Run getRun() {
    return run;
  }

  public List<FixResult> getResults() {
    return results;
  }

  /** Returns true if the CodeTF describes any changes made to the code. */
  public boolean hasCodeChanges() {
    return results.stream()
        .filter(result -> result.getFixStatus().getStatus() == FixStatusType.FIXED)
        .anyMatch(result -> !result.getChangeSets().isEmpty());
  }
}
