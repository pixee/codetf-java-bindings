package io.codemodder.codetf;

import java.util.Objects;

/**
 * Describes a detected finding that served as input to the codemod.
 *
 * <p>When a codemod is able to fix a finding, it should create a {@link FixedFinding} instance. If
 * a codemod would typically fix findings of this type but cannot, it can create an {@link
 * UnfixedFinding} instance to explain why.
 *
 * <p>Findings typically have some ID specified in the detector results.
 */
public abstract sealed class Finding permits FixedFinding, UnfixedFinding {

  private final String id;
  private final DetectorRule rule;

  Finding(final String id, final DetectorRule rule) {
    this.id = id;
    this.rule = Objects.requireNonNull(rule);
  }

  Finding(final DetectorRule rule) {
    this(null, rule);
  }

  /**
   * @return the ID of the finding, or {@code null} if the finding has no ID
   */
  public String getId() {
    return id;
  }

  /**
   * @return the rule that detected the finding
   */
  public DetectorRule getRule() {
    return rule;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Finding finding = (Finding) o;
    return Objects.equals(id, finding.id) && rule.equals(finding.rule);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(id);
    result = 31 * result + rule.hashCode();
    return result;
  }
}
