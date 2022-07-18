# Code Transformation Format - Java Bindings

This library provides domain objects for interacting with [Code Transformation Format (CodeTF)](https://github.com/pixeeworks/codetf/) files.

# Features

These objects offer the following attributes:

* Decorated with [Jackson](https://github.com/FasterXML/jackson) annotations 
* Sanity checking CodeTF requirements
* Immutability
* JavaDocs to help you understand what data is expected

# Getting Started

To deserialize a CodeTF file using these objects, simply deserialize with Jackson's typical APIs:

```java
ObjectMapper mapper = new ObjectMapper();
CodeTFReport report = mapper.readValue(codetfFile, CodeTFReport.class);
```
