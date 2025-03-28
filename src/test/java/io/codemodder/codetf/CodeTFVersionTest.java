package io.codemodder.codetf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codemodder.codetf.v3.CodeTF;
import io.codemodder.codetf.v3.FixStatusType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class CodeTFVersionTest {

  @Test
  public void testDetectV2Format() throws IOException {
    String v2Json =
        "{\n"
            + "  \"run\": {\n"
            + "    \"tool\": \"example-tool\",\n"
            + "    \"version\": \"1.0.0\",\n"
            + "    \"commandLine\": \"example command\",\n"
            + "    \"elapsed\": 1000,\n"
            + "    \"directory\": \"/example/dir\",\n"
            + "    \"vendor\": \"example-vendor\"\n"
            + "  },\n"
            + "  \"results\": [\n"
            + "    {\n"
            + "      \"codemod\": \"example-codemod\",\n"
            + "      \"summary\": \"Example summary\",\n"
            + "      \"description\": \"Example description\",\n"
            + "      \"changeset\": []\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    ObjectMapper mapper = new ObjectMapper();
    CodeTFVersion version = CodeTFDeserializer.detectVersion(mapper.readTree(v2Json));
    assertThat(version, is(CodeTFVersion.V2));

    InputStream is = new ByteArrayInputStream(v2Json.getBytes(StandardCharsets.UTF_8));
    CodeTFReport report = CodeTFLoader.loadReport(is);
    assertThat(report, notNullValue());
    assertThat(report.getRun().getVendor(), is("example-vendor"));
    assertThat(report.getRun().getTool(), is("example-tool"));
  }

  @Test
  public void testDetectV3Format() throws IOException {
    String v3Json =
        "{\n"
            + "  \"run\": {\n"
            + "    \"tool\": \"example-tool\",\n"
            + "    \"version\": \"1.0.0\",\n"
            + "    \"vendor\": \"example-vendor\"\n"
            + "  },\n"
            + "  \"results\": [\n"
            + "    {\n"
            + "      \"finding\": {\n"
            + "        \"id\": \"finding-1\",\n"
            + "        \"rule\": {\n"
            + "          \"id\": \"rule-1\",\n"
            + "          \"name\": \"Rule 1\"\n"
            + "        }\n"
            + "      },\n"
            + "      \"fixStatus\": {\n"
            + "        \"status\": \"fixed\"\n"
            + "      },\n"
            + "      \"changeSets\": [\n"
            + "        {\n"
            + "          \"path\": \"example.java\",\n"
            + "          \"diff\": \"diff content\",\n"
            + "          \"changes\": [\n"
            + "            {\n"
            + "              \"lineNumber\": 42,\n"
            + "              \"diffSide\": \"right\"\n"
            + "            }\n"
            + "          ]\n"
            + "        }\n"
            + "      ],\n"
            + "      \"fixMetadata\": {\n"
            + "        \"id\": \"fix-1\",\n"
            + "        \"summary\": \"Summary\",\n"
            + "        \"description\": \"Description\",\n"
            + "        \"references\": [\n"
            + "          {\n"
            + "            \"url\": \"https://example.com\"\n"
            + "          }\n"
            + "        ],\n"
            + "        \"generation\": {\n"
            + "          \"strategy\": \"deterministic\",\n"
            + "          \"provisional\": false\n"
            + "        }\n"
            + "      }\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    ObjectMapper mapper = new ObjectMapper();
    CodeTFVersion version = CodeTFDeserializer.detectVersion(mapper.readTree(v3Json));
    assertThat(version, is(CodeTFVersion.V3));

    InputStream is = new ByteArrayInputStream(v3Json.getBytes(StandardCharsets.UTF_8));
    CodeTF v3Report = CodeTFLoader.loadV3Report(is);
    assertThat(v3Report, notNullValue());
    assertThat(v3Report.getRun().getVendor(), is("example-vendor"));
    assertThat(v3Report.getRun().getTool(), is("example-tool"));
    assertThat(v3Report.getResults().get(0).getFixStatus().getStatus(), is(FixStatusType.FIXED));
  }
}
