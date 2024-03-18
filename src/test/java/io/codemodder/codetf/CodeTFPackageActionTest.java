package io.codemodder.codetf;

import static io.codemodder.codetf.CodeTFPackageAction.CodeTFPackageActionResult.COMPLETED;
import static io.codemodder.codetf.CodeTFPackageAction.CodeTFPackageActionType.ADD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** Unit tests for {@link CodeTFPackageAction}. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
final class CodeTFPackageActionTest {

  private ObjectReader reader;

  @BeforeAll
  void before() {
    reader = new ObjectMapper().readerFor(CodeTFPackageAction.class);
  }

  /**
   * Verifies that the enums in {@link CodeTFPackageAction} respect both all lowercase and all
   * uppercase strings.
   */
  @SuppressWarnings("ConcatenationWithEmptyString") // for readability
  @ValueSource(
      strings = {
        ""
            + "{\n"
            + "    \"action\" : \"add\",\n"
            + "    \"result\": \"completed\",\n"
            + "    \"package\" : \"pkg:maven/io.github.pixee/java-security-toolkit@1.0.2\"\n"
            + "}",
        ""
            + "{\n"
            + "    \"action\" : \"ADD\",\n"
            + "    \"result\": \"COMPLETED\",\n"
            + "    \"package\" : \"pkg:maven/io.github.pixee/java-security-toolkit@1.0.2\"\n"
            + "}"
      })
  @ParameterizedTest
  void it_deserializes_enums_correctly(final String json) throws JsonProcessingException {
    // WHEN deserialize using an out-of-the-box ObjectMapper
    final CodeTFPackageAction action = reader.readValue(json);

    // THEN deserializes correctly
    final CodeTFPackageAction expected =
        new CodeTFPackageAction(
            ADD, COMPLETED, "pkg:maven/io.github.pixee/java-security-toolkit@1.0.2");
    assertThat(action, equalTo(expected));
  }
}
