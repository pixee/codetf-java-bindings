# Code Change Format - Java SDK

This library provides domain objects for interacting with [Code Change Format](https://github.com/pixeeworks/ccf/) (CCF) files.

# Features

These objects offer the following attributes:

* Decorated with [Jackson](https://github.com/FasterXML/jackson) annotations 
* Sanity checking CCF requirements
* Immutability
* JavaDocs to help you understand what data is expected

# Getting Started

To deserialize a CCF file using these objects, simply deserialize with Jackson's regular API:

```java
ObjectMapper mapper = new ObjectMapper();
CCFReport report = mapper.readValue(ccfFile, CCFReport.class);
```
