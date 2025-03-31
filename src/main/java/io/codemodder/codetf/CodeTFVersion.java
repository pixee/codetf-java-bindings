package io.codemodder.codetf;

/**
 * Defines the supported versions of the CodeTF format.
 *
 * <p>Note: Applications should explicitly use either the V2 or V3 format classes for
 * deserialization rather than attempting to auto-detect the format version.
 */
public enum CodeTFVersion {
  /** The original CodeTF format (v2). */
  V2,

  /** The newer V3 format. */
  V3
}
