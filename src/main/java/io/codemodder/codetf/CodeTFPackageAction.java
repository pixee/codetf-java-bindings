package io.codemodder.codetf;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class CodeTFPackageAction {

  public enum CodeTFPackageActionType {
    @JsonAlias("add")
    ADD,
    @JsonAlias("remove")
    REMOVE
  }

  public enum CodeTFPackageActionResult {
    @JsonAlias("completed")
    COMPLETED,
    @JsonAlias("failed")
    FAILED,
    @JsonAlias("skipped")
    SKIPPED
  }

  private final String packageUrl;
  private final CodeTFPackageActionResult result;
  private final CodeTFPackageActionType action;

  @JsonCreator
  public CodeTFPackageAction(
      @JsonProperty("action") final CodeTFPackageActionType action,
      @JsonProperty("result") final CodeTFPackageActionResult result,
      @JsonProperty("package") final String packageUrl) {
    this.packageUrl = CodeTFValidator.requireNonBlank(packageUrl);
    this.result = Objects.requireNonNull(result);
    this.action = Objects.requireNonNull(action);
  }

  public String getPackage() {
    return packageUrl;
  }

  public CodeTFPackageActionResult getResult() {
    return result;
  }

  public CodeTFPackageActionType getAction() {
    return action;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final CodeTFPackageAction that = (CodeTFPackageAction) o;

    if (!packageUrl.equals(that.packageUrl)) return false;
    if (result != that.result) return false;
    return action == that.action;
  }

  @Override
  public int hashCode() {
    int result1 = packageUrl.hashCode();
    result1 = 31 * result1 + result.hashCode();
    result1 = 31 * result1 + action.hashCode();
    return result1;
  }

  @Override
  public String toString() {
    return "CodeTFPackageAction{"
        + "packageUrl='"
        + packageUrl
        + '\''
        + ", result="
        + result
        + ", action="
        + action
        + '}';
  }
}
