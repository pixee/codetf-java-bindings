# Code Transformation Format - Java Bindings

This library provides domain objects for interacting with [Code Transformation Format (CodeTF)](https://github.com/pixee/codemodder-specs/) files.

# Features

These objects offer the following attributes:

* Decorated with [Jackson](https://github.com/FasterXML/jackson) annotations 
* Sanity checking CodeTF requirements
* Immutability
* JavaDocs to help you understand what data is expected
* Support for multiple CodeTF versions (v2 and v3)

# Adding to your project

To deserialize a CodeTF file using these objects, simply deserialize with Jackson's typical APIs:

## Gradle
```kotlin
implementation("io.codemodder:codetf-java:4.5.0")
```

## Maven
```xml
<dependency>
  <groupId>io.codemodder</groupId>
  <artifactId>codetf-java</artifactId>
  <version>4.5.0</version>
</dependency>
```

## Using in your project

### Working with original CodeTF format (v2)

Using Jackson directly:
```java
ObjectMapper mapper = new ObjectMapper();
CodeTFReport report = mapper.readValue(codetfFile, CodeTFReport.class);
```

### Working with the new CodeTF v3 format

Using the CodeTFLoader utility (recommended):
```java
// Auto-detect format version and load as v2 format (backward compatibility)
CodeTFReport report = CodeTFLoader.loadReport(inputStream);

// Explicitly load as v3 format
io.codemodder.codetf.v3.CodeTF v3Report = CodeTFLoader.loadV3Report(inputStream);

// Detect format version
CodeTFVersion version = CodeTFLoader.detectVersion(inputStream);
```

The library automatically detects the format version based on the JSON structure, so you can use the same loading code for both formats.

## Running Formatter

To run the autoformatter, run the following command:

```shell
mvn fmt:format
```
