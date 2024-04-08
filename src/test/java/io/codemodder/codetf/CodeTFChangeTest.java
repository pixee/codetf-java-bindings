package io.codemodder.codetf;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

final class CodeTFChangeTest {

  @Test
  void it_raises_npe_on_null_diff_side() {
    assertThrows(
        NullPointerException.class,
        () -> new CodeTFChange(1, null, null, null, null, null, List.of()));
  }

  @Test
  void it_raises_iae_on_negative_line_number() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new CodeTFChange(-1, null, null, CodeTFDiffSide.LEFT, null, null, List.of()));
  }

  @Test
  void it_creates_change() {
    CodeTFChange change =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertNotNull(change);
    assertEquals(1, change.getLineNumber());
    assertEquals(CodeTFDiffSide.LEFT, change.getDiffSide());
  }

  @Test
  void it_creates_change_with_properties() {
    CodeTFChange change =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertNotNull(change);
    assertEquals(1, change.getLineNumber());
    assertEquals(CodeTFDiffSide.LEFT, change.getDiffSide());
    assertNotNull(change.getProperties());
  }

  @Test
  void equals_and_hash_code() {
    CodeTFChange change1 =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    CodeTFChange change2 =
        new CodeTFChange(1, null, null, CodeTFDiffSide.LEFT, null, null, List.of());
    assertEquals(change1, change2);
    assertEquals(change1.hashCode(), change2.hashCode());
  }
}
