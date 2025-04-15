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
implementation("io.codemodder:codetf-java:4.6.0")
```

## Maven
```xml
<dependency>
  <groupId>io.codemodder</groupId>
  <artifactId>codetf-java</artifactId>
  <version>4.6.0</version>
</dependency>
```

## Using in your project

This library provides properly annotated data classes for both CodeTF v2 and v3 formats. 
You should choose which format to use based on your requirements and explicitly deserialize to that format.

### Working with original CodeTF format (v2)

```java
ObjectMapper mapper = new ObjectMapper();
CodeTFReport report = mapper.readValue(codetfFile, CodeTFReport.class);
```

### Working with the new CodeTF v3 format

```java
ObjectMapper mapper = new ObjectMapper();
io.codemodder.codetf.v3.CodeTF v3Report = mapper.readValue(codetfFile, io.codemodder.codetf.v3.CodeTF.class);
```

This approach allows applications to use their own centralized ObjectMapper with custom configurations.

## Running Formatter

To run the autoformatter, run the following command:

```shell
mvn fmt:format
```
