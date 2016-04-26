# Util Library Change Log

Note that the "Build Tools" component is decoupled and has its own change log.

## 1.4

* Provide DataSource factory that returns a pooled data source, rather than a DB connection (fixes #12)
* `org.beiter.michael.array.Converter` class does not document `UnsupportedCharsetException` (fixes #11)
* License Maven Plugin version is hardcoded (fixes #10)

## 1.3

* Upgrade build tools used in util lib to 1.2 (fixes #2)
* Runtime exceptions thrown by the Validate.*() methods should be documented (fixes #5)
* Upgrade build tools used in util lib to 1.3 (fixes #8)
* First pass on array utilities implementation (fixes #9)
* AdditionalProperties in properties should never be null (fixes #7)

## 1.2

* Parsed properties contain a copy of the original properties (fixes #6)
* Connection properties support defensive copying via copy constructor (fixes #1)

## 1.1

Changed the configuration mechanism to use a properties POJO and one (or more) properties builder(s). Properties
Builders can extract properties from various sources, and produce a properties POJO. A default implementation
that extracts properties of a Map<String, String> is provided in this implementation.

Library consumers can use their own mechanism to populate the properties POJO. Examples include Spring configuration,
injection mechanisms, creating a new builder that uses Java Properties, etc.

## 1.0

Initial release
