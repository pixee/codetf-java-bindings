package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;

/** Metadata about the analysis run that produced the results. */
public final class Run {
  private final String vendor;
  private final String tool;
  private final String version;
  private final String projectMetadata;
  private final Integer elapsed;
  private final Map<String, Object> inputMetadata;
  private final Map<String, Object> analysisMetadata;

  @JsonCreator
  public Run(
      @JsonProperty("vendor") String vendor,
      @JsonProperty("tool") String tool,
      @JsonProperty("version") String version,
      @JsonProperty("projectMetadata") String projectMetadata,
      @JsonProperty("elapsed") Integer elapsed,
      @JsonProperty("inputMetadata") Map<String, Object> inputMetadata,
      @JsonProperty("analysisMetadata") Map<String, Object> analysisMetadata) {
    this.vendor = Objects.requireNonNull(vendor, "vendor cannot be null");
    this.tool = Objects.requireNonNull(tool, "tool cannot be null");
    this.version = Objects.requireNonNull(version, "version cannot be null");
    this.projectMetadata = projectMetadata;
    this.elapsed = elapsed;
    this.inputMetadata = inputMetadata;
    this.analysisMetadata = analysisMetadata;
  }

  public String getVendor() {
    return vendor;
  }

  public String getTool() {
    return tool;
  }

  public String getVersion() {
    return version;
  }

  public String getProjectMetadata() {
    return projectMetadata;
  }

  public Integer getElapsed() {
    return elapsed;
  }

  public Map<String, Object> getInputMetadata() {
    return inputMetadata;
  }

  public Map<String, Object> getAnalysisMetadata() {
    return analysisMetadata;
  }
}
