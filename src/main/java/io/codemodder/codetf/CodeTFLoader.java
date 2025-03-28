package io.codemodder.codetf;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codemodder.codetf.v3.CodeTF;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/** Utility class for loading and parsing CodeTF reports. */
public final class CodeTFLoader {

  private CodeTFLoader() {
    // Private constructor to prevent instantiation
  }

  /**
   * Load a CodeTF report from a file, automatically detecting the format version. This will return
   * a CodeTFReport (v2 format) regardless of the input version.
   *
   * @param file The file containing the JSON data
   * @return The deserialized CodeTFReport
   * @throws IOException If there is an error reading from the file
   */
  public static CodeTFReport loadReport(File file) throws IOException {
    try (InputStream is = new FileInputStream(file)) {
      return CodeTFDeserializer.deserialize(is);
    }
  }

  /**
   * Load a CodeTF report from an input stream, automatically detecting the format version. This
   * will return a CodeTFReport (v2 format) regardless of the input version.
   *
   * @param inputStream The input stream containing the JSON data
   * @return The deserialized CodeTFReport
   * @throws IOException If there is an error reading from the input stream
   */
  public static CodeTFReport loadReport(InputStream inputStream) throws IOException {
    return CodeTFDeserializer.deserialize(inputStream);
  }

  /**
   * Load a CodeTF v3 report from a file.
   *
   * @param file The file containing the JSON data
   * @return The deserialized CodeTF v3 report
   * @throws IOException If there is an error reading from the file
   */
  public static CodeTF loadV3Report(File file) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(file, CodeTF.class);
  }

  /**
   * Load a CodeTF v3 report from an input stream.
   *
   * @param inputStream The input stream containing the JSON data
   * @return The deserialized CodeTF v3 report
   * @throws IOException If there is an error reading from the input stream
   */
  public static CodeTF loadV3Report(InputStream inputStream) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(inputStream, CodeTF.class);
  }

  /**
   * Detect the version of a CodeTF report from a file.
   *
   * @param file The file containing the JSON data
   * @return The detected CodeTF version
   * @throws IOException If there is an error reading from the file
   */
  public static CodeTFVersion detectVersion(File file) throws IOException {
    try (InputStream is = new FileInputStream(file)) {
      ObjectMapper mapper = new ObjectMapper();
      return CodeTFDeserializer.detectVersion(mapper.readTree(is));
    }
  }

  /**
   * Detect the version of a CodeTF report from an input stream.
   *
   * @param inputStream The input stream containing the JSON data
   * @return The detected CodeTF version
   * @throws IOException If there is an error reading from the input stream
   */
  public static CodeTFVersion detectVersion(InputStream inputStream) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return CodeTFDeserializer.detectVersion(mapper.readTree(inputStream));
  }
}
