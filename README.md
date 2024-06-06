This is a Kotlin Multi Platform library for generating [Universally Unique Identifiers](https://en.wikipedia.org/wiki/Universally_unique_identifier).

support: android、ios、jvm

List of implemented UUID types:

*   __UUID Version 1__: the Gregorian time-based type specified in RFC-4122;
*   __UUID Version 2__: the DCE Security type with embedded POSIX UIDs specified in DCE 1.1;
*   __UUID Version 3__: the name-based type specified in RFC-4122 that uses MD5 hashing;
*   __UUID Version 4__: the randomly or pseudo-randomly generated type specified in RFC-4122;
*   __UUID Version 5__: the name-based type specified in RFC-4122 that uses SHA1 hashing;
*   __UUID Version 6__: the reordered Gregorian time-based type specified as a [new UUID format](https://datatracker.ietf.org/doc/draft-ietf-uuidrev-rfc4122bis/);
*   __UUID Version 7__: the Unix Epoch time-based type specified as a [new UUID format](https://datatracker.ietf.org/doc/draft-ietf-uuidrev-rfc4122bis/).

### `UUID`

- Frozen
- Thread-safe (thread-safe randomness in native)
- Adheres to RFC4122
- Tested
- Tested against macOS/iOS UUID to verify correctness

### Setup

In your build.gradle(.kts):

This library publishes gradle module metadata. If you're using Gradle prior to version 6, you should have `enableFeaturePreview("GRADLE_METADATA")` in your settings.gradle(.kts).

### Future Goals

- Develop UUID functionality that can be contributed back to the Kotlin stdlib (see latest issues, PRs, and CHANGELOG.md for updates)