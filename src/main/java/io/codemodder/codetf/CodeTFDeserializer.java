package io.codemodder.codetf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;

/** Deserializer for CodeTF reports that can handle different versions. */
public class CodeTFDeserializer extends StdDeserializer<CodeTFReport> {

  private static final long serialVersionUID = 1L;
  private final ObjectMapper mapper = new ObjectMapper();

  public CodeTFDeserializer() {
    this(null);
  }

  public CodeTFDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public CodeTFReport deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    ObjectNode root = jp.getCodec().readTree(jp);
    return deserializeReport(root);
  }

  /**
   * Deserialize a CodeTF report from an input stream, automatically detecting the format version.
   *
   * @param inputStream The input stream containing the JSON data
   * @return The deserialized CodeTFReport
   * @throws IOException If there is an error reading from the input stream
   */
  public static CodeTFReport deserialize(InputStream inputStream) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(inputStream);
    return deserializeReport(rootNode);
  }

  private static CodeTFReport deserializeReport(JsonNode rootNode) throws IOException {
    CodeTFVersion version = detectVersion(rootNode);
    ObjectMapper mapper = new ObjectMapper();

    switch (version) {
      case V3:
        io.codemodder.codetf.v3.CodeTF v3Report =
            mapper.treeToValue(rootNode, io.codemodder.codetf.v3.CodeTF.class);
        return convertV3ToV2(v3Report);
      case V2:
      default:
        return mapper.treeToValue(rootNode, CodeTFReport.class);
    }
  }

  /**
   * Detect the version of the CodeTF format based on the structure of the JSON.
   *
   * @param rootNode The root JSON node
   * @return The detected CodeTF version
   */
  public static CodeTFVersion detectVersion(JsonNode rootNode) {
    // Check for v3 indicators
    if (rootNode.has("results") && rootNode.get("results").isArray()) {
      JsonNode firstResult =
          rootNode.get("results").size() > 0 ? rootNode.get("results").get(0) : null;
      if (firstResult != null
          && (firstResult.has("finding")
              || firstResult.has("fixStatus")
              || firstResult.has("changeSets"))) {
        return CodeTFVersion.V3;
      }
    }

    // Default to V2
    return CodeTFVersion.V2;
  }

  /**
   * Convert a V3 format report to the V2 format.
   *
   * @param v3Report The V3 report to convert
   * @return The equivalent V2 report
   */
  private static CodeTFReport convertV3ToV2(io.codemodder.codetf.v3.CodeTF v3Report) {
    // Create a CodeTFRun from the v3 Run
    CodeTFRun run =
        new CodeTFRun(
            v3Report.getRun().getVendor(),
            v3Report.getRun().getTool(),
            v3Report.getRun().getVersion(),
            null, // No equivalent in v3 for the commandLine
            v3Report.getRun().getElapsed() != null
                ? v3Report.getRun().getElapsed().longValue()
                : 0L,
            null, // No equivalent in v3 for the directory
            java.util.Collections.emptyList() // No equivalent in v3 for sarifs
            );

    // Convert the results - this is just a simple conversion for now
    // In a full implementation, you'd want to map all the fields appropriately
    return new CodeTFReport(run, java.util.Collections.emptyList());
  }
}
