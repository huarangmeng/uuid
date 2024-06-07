This is a Kotlin Multiplatform library for generating [Universally Unique Identifiers](https://en.wikipedia.org/wiki/Universally_unique_identifier).

support: android、ios、jvm

The Internet standard [RFC 9562](https://www.rfc-editor.org/rfc/rfc9562) was published in May 2024, making RFC 4122 obsolete. This library is fully compliant with the new RFC, as it was developed following the evolution of the new standard until its publication.

List of implemented UUID subtypes:

*   __UUID Version 1__: the Gregorian time-based UUID specified in RFC 9562;
*   __UUID Version 2__: the DCE Security version, with embedded POSIX UIDs, specified in DCE 1.1;
*   __UUID Version 3__: the name-based version that uses MD5 hashing specified in RFC 9562;
*   __UUID Version 4__: The randomly or pseudorandomly generated version specified in RFC 9562;
*   __UUID Version 5__: the name-based version that uses SHA-1 hashing specified in RFC 9562;
*   __UUID Version 6__: the reordered Gregorian time-based UUID specified in RFC 9562;
*   __UUID Version 7__: the Unix Epoch time-based UUID specified in RFC 9562.

### `UUID`

- Frozen
- Thread-safe (thread-safe randomness in native)
- Adheres to RFC 9562
- Tested
- Tested against macOS/iOS UUID to verify correctness

### Setup

In your build.gradle(.kts):

This library publishes gradle module metadata. If you're using Gradle prior to version 6, you should have `enableFeaturePreview("GRADLE_METADATA")` in your settings.gradle(.kts).

### Future Goals

- Develop UUID functionality that can be contributed back to the Kotlin stdlib (see latest issues, PRs, and CHANGELOG.md for updates)