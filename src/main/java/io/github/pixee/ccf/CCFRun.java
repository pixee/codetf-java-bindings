package io.github.pixee.ccf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Describes the "run" section of a CCF report. */
public final class CCFRun {

  private final String vendor;

  private final String tool;

  private final String commandLine;

  private final Long elapsed;

  private final CCFConfiguration configuration;

  private final List<CCFFileExtensionScanned> filesScanned;

  private final List<String> failedFiles;

  @JsonCreator
  public CCFRun(
      @JsonProperty("vendor") final String vendor,
      @JsonProperty("tool") final String tool,
      @JsonProperty("commandLine") final String commandLine,
      @JsonProperty("elapsed") final Long elapsed,
      @JsonProperty("fileExtensionsScanned") final List<CCFFileExtensionScanned> filesScanned,
      @JsonProperty("configuration") final CCFConfiguration configuration,
      @JsonProperty("failedFiles") final List<String> failedFiles) {
    this.vendor = CCFValidator.requireNonBlank(vendor);
    this.tool = CCFValidator.requireNonBlank(tool);
    this.commandLine = commandLine;

    if (elapsed <= 0) {
      throw new IllegalArgumentException("elapsed must be a positive value");
    }
    this.elapsed = elapsed;
    this.configuration = Objects.requireNonNull(configuration, "configuration");

    this.filesScanned = Objects.requireNonNullElse(filesScanned, Collections.emptyList());

    this.failedFiles = Objects.requireNonNullElse(failedFiles, Collections.emptyList());
  }

  public String getVendor() {
    return vendor;
  }

  public String getTool() {
    return tool;
  }

  public Long getElapsed() {
    return elapsed;
  }

  public String getCommandLine() {
    return commandLine;
  }

  public CCFConfiguration getConfiguration() {
    return configuration;
  }

  public List<String> getFailedFiles() {
    return failedFiles;
  }

  public List<CCFFileExtensionScanned> getFilesScanned() {
    return filesScanned;
  }
}
