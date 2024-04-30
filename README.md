K/N doesn't have a UUID yet. This brings a UUID that matches UUIDs on various platforms:

- iOS/Mac: `NSUUID`
- Java: `java.util.UUID`

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