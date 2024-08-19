package io.codemodder.codetf;

/** Unit tests for {@link FixedFinding}. */
final class FixedFindingTest implements EqualsAndHashcodeTests<FixedFinding> {

  @Override
  public FixedFinding createInstance() {
    final var rule =
        new DetectorRule(
            "xxe",
            "XML External Entities",
            "https://owasp.org/www-community/vulnerabilities/XML_External_Entity_(XXE)_Processing");
    return new FixedFinding(rule);
  }

  @Override
  public FixedFinding createDifferentInstance() {
    return new FixedFinding(
        "sql-injection/foo",
        new DetectorRule(
            "sqli",
            "SQL Injection",
            "https://owasp.org/www-community/vulnerabilities/SQL_Injection"));
  }
}
