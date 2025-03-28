package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Result corresponding to a single finding. */
public final class FixResult {
  private final Finding finding;
  private final FixStatus fixStatus;
  private final List<ChangeSet> changeSets;
  private final FixMetadata fixMetadata;
  private final FixQuality fixQuality;
  private final List<String> reasoningSteps;

  @JsonCreator
  public FixResult(
      @JsonProperty("finding") Finding finding,
      @JsonProperty("fixStatus") FixStatus fixStatus,
      @JsonProperty("changeSets") List<ChangeSet> changeSets,
      @JsonProperty("fixMetadata") FixMetadata fixMetadata,
      @JsonProperty("fixQuality") FixQuality fixQuality,
      @JsonProperty("reasoningSteps") List<String> reasoningSteps) {
    this.finding = Objects.requireNonNull(finding, "finding cannot be null");
    this.fixStatus = Objects.requireNonNull(fixStatus, "fixStatus cannot be null");
    this.changeSets = changeSets != null ? changeSets : Collections.emptyList();
    this.fixMetadata = fixMetadata;
    this.fixQuality = fixQuality;
    this.reasoningSteps = reasoningSteps;

    // Validate that fixed results have required fields
    if (fixStatus.getStatus() == FixStatusType.FIXED) {
      if (changeSets == null || changeSets.isEmpty()) {
        throw new IllegalArgumentException("changeSets must be provided for fixed results");
      }
      if (fixMetadata == null) {
        throw new IllegalArgumentException("fixMetadata must be provided for fixed results");
      }
    }
  }

  public Finding getFinding() {
    return finding;
  }

  public FixStatus getFixStatus() {
    return fixStatus;
  }

  public List<ChangeSet> getChangeSets() {
    return changeSets;
  }

  public FixMetadata getFixMetadata() {
    return fixMetadata;
  }

  public FixQuality getFixQuality() {
    return fixQuality;
  }

  public List<String> getReasoningSteps() {
    return reasoningSteps;
  }
}
