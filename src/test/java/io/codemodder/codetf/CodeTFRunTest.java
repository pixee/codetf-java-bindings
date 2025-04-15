package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import org.junit.jupiter.api.Test;

final class CodeTFRunTest {

  @Test
  void it_creates_run_with_valid_values() {
    CodeTFRun run =
        new CodeTFRun(
            "vendor",
            "tool",
            "1.0.0",
            "command --arg",
            10L,
            "/path/to/dir",
            Collections.emptyList());

    assertNotNull(run);
    assertEquals("vendor", run.getVendor());
    assertEquals("tool", run.getTool());
    assertEquals("1.0.0", run.getVersion());
    assertEquals("command --arg", run.getCommandLine());
    assertEquals(10L, run.getElapsed());
    assertEquals("/path/to/dir", run.getDirectory());
    assertTrue(run.getSarifs().isEmpty());
  }

  @Test
  void it_allows_zero_elapsed_time() {
    CodeTFRun run =
        new CodeTFRun(
            "vendor",
            "tool",
            "1.0.0",
            "command --arg",
            0L,
            "/path/to/dir",
            Collections.emptyList());

    assertNotNull(run);
    assertEquals(0L, run.getElapsed());
  }

  @Test
  void it_rejects_negative_elapsed_time() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                new CodeTFRun(
                    "vendor",
                    "tool",
                    "1.0.0",
                    "command --arg",
                    -1L,
                    "/path/to/dir",
                    Collections.emptyList()));

    assertEquals("elapsed must be a non-negative value", exception.getMessage());
  }

  @Test
  void it_validates_required_fields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new CodeTFRun(
                "", "tool", "1.0.0", "command --arg", 0L, "/path/to/dir", Collections.emptyList()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new CodeTFRun(
                "vendor",
                "",
                "1.0.0",
                "command --arg",
                0L,
                "/path/to/dir",
                Collections.emptyList()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new CodeTFRun(
                "vendor",
                "tool",
                "",
                "command --arg",
                0L,
                "/path/to/dir",
                Collections.emptyList()));

    assertThrows(
        NullPointerException.class,
        () ->
            new CodeTFRun(
                "vendor", "tool", "1.0.0", null, 0L, "/path/to/dir", Collections.emptyList()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new CodeTFRun(
                "vendor", "tool", "1.0.0", "command --arg", 0L, "", Collections.emptyList()));
  }
}
