package io.github.pixee.ccf;

import static io.github.pixee.ccf.CCFValidator.toImmutableCopyOrEmptyOnNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Describes the "configuration" section of the CCF report. */
public final class CCFConfiguration {

  private final String directory;

  private final List<String> includes;

  private final List<String> excludes;

  private final List<String> modules;

  private final List<CCFInput> inputs;

  @JsonCreator
  public CCFConfiguration(
      @JsonProperty("directory") final String directory,
      @JsonProperty("includes") final List<String> includes,
      @JsonProperty("excludes") final List<String> excludes,
      @JsonProperty("modules") final List<String> modules,
      @JsonProperty("inputs") final List<CCFInput> inputs) {
    this.directory = CCFValidator.requireNonBlank(directory);
    this.includes = CCFValidator.toImmutableCopyOrEmptyOnNull(includes);
    this.excludes = CCFValidator.toImmutableCopyOrEmptyOnNull(excludes);
    this.modules = CCFValidator.toImmutableCopyOrEmptyOnNull(modules);
    this.inputs = toImmutableCopyOrEmptyOnNull(inputs);
  }

  public List<CCFInput> getInputs() {
    return inputs;
  }

  public List<String> getModules() {
    return modules;
  }

  public List<String> getExcludes() {
    return excludes;
  }

  public List<String> getIncludes() {
    return includes;
  }

  public String getDirectory() {
    return directory;
  }
}
