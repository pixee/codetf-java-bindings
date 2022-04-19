package io.github.pixee.ccf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Describes an "input" for the CCF report. */
public final class CCFInput {

  private final String artifact;

  private final String sha1;

  private final String vendor;

  @JsonCreator
  public CCFInput(
      @JsonProperty("artifact") final String artifact,
      @JsonProperty("sha1") final String sha1,
      @JsonProperty("vendor") final String vendor) {
    this.artifact = CCFValidator.requireNonBlank(artifact);
    this.sha1 = CCFValidator.requireNonBlank(sha1);
    this.vendor = CCFValidator.requireNonBlank(vendor);
  }

  public String getArtifact() {
    return artifact;
  }

  public String getSha1() {
    return sha1;
  }

  public String getVendor() {
    return vendor;
  }
}
