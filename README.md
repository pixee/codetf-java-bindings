# Code Transformation Format - Java Bindings

This library provides domain objects for interacting with [Code Transformation Format (CodeTF)](https://github.com/pixee/codemodder-specs/) files.

# Features

These objects offer the following attributes:

* Decorated with [Jackson](https://github.com/FasterXML/jackson) annotations 
* Sanity checking CodeTF requirements
* Immutability
* JavaDocs to help you understand what data is expected

# Adding to your project

To deserialize a CodeTF file using these objects, simply deserialize with Jackson's typical APIs:

## Gradle
```kotlin
implementation("io.codemodder:codetf-java:4.3.0")
```

## Maven
```xml
<dependency>
  <groupId>io.codemodder</groupId>
  <artifactId>codetf-java</artifactId>
  <version>4.3.0</version>
</dependency>
```

## Using in your project

```java
ObjectMapper mapper = new ObjectMapper();
CodeTFReport report = mapper.readValue(codetfFile, CodeTFReport.class);
```

## Running Formatter

To run the autoformatter, run the following command:

```shell
mvn fmt:format
```
