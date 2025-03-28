package io.codemodder.codetf.v3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/** An action taken on a package. */
public final class PackageAction {
  private final Action action;
  private final PackageResult result;
  private final String packageName;

  @JsonCreator
  public PackageAction(
      @JsonProperty("action") Action action,
      @JsonProperty("result") PackageResult result,
      @JsonProperty("package") String packageName) {
    this.action = Objects.requireNonNull(action, "action cannot be null");
    this.result = Objects.requireNonNull(result, "result cannot be null");
    this.packageName = Objects.requireNonNull(packageName, "package cannot be null");
  }

  public Action getAction() {
    return action;
  }

  public PackageResult getResult() {
    return result;
  }

  @JsonProperty("package")
  public String getPackageName() {
    return packageName;
  }
}
