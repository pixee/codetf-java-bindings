package io.codemodder.codetf;

/** Unit tests for {@link UnfixedFinding}. */
final class UnfixedFindingTest implements EqualsAndHashcodeTests<UnfixedFinding> {

  @Override
  public UnfixedFinding createInstance() {
    final var rule =
        new DetectorRule(
            "xxe",
            "XML External Entities",
            "https://owasp.org/www-community/vulnerabilities/XML_External_Entity_(XXE)_Processing");
    return new UnfixedFinding(rule, "src/main/java/com/acme/Foo.java", 42, "This is bad");
  }

  @Override
  public UnfixedFinding createDifferentInstance() {
    final var rule =
        new DetectorRule(
            "sqli",
            "SQL Injection",
            "https://owasp.org/www-community/vulnerabilities/SQL_Injection");
    return new UnfixedFinding(
        "sql-injection/foo", rule, "src/main/java/com/acme/Bar.java", 84, "This is also bad");
  }
}
