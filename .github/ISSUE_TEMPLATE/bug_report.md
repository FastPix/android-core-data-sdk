---
name: Bug Report
about: Create a report to help us improve
title: '[BUG] '
labels: bug
assignees: ''
---

## Bug Description
A clear and concise description of what the bug is.

## Reproduction Steps

1. **Setup Environment**

```groovy
dependencies {
    implementation 'io.fastpix.data:media3:1.2.2'
}
```

2. **Code To Reproduce**

```kotlin
 val config = SDKConfiguration(
    workspaceId = "your-workspace-id",
    beaconUrl = "custom.beacon.url", // Optional
    // Optional
    videoData = VideoDataDetails(
        videoId = "video-123",
        videoTitle = "My Awesome Video",
        videoDuration = "300000", // in milliseconds
        videoThumbnail = "https://example.com/thumbnail.jpg",
        fpPlaybackId = "playback-id" // Optional FastPix playback ID
        //.. etc
    ),
    // Optional
    playerData = PlayerDataDetails(
        playerName = "ExoPlayer",
        playerVersion = "2.19.0"
    ),
    playerListener = MyPlayerListener(),
    enableLogging = true, // Enable for debugging
    // Optional
    customData = CustomDataDetails(
        customField1 = "custom-value-1",
        customField2 = "custom-value-2"
        //.. etc
    )
)

// Initialize SDK
fastPixSDK.initialize(config, applicationContext)
```

3. **Expected Behavior**
```
<!-- A clear and concise description of what you expected to happen.  -->
```

4. **Actual Behavior**
```
<!-- A clear and concise description of what actually happened. -->
```

5. **Environment**

- **SDK Version**: [e.g., 1.2.2]
- **Android Version**: [e.g., Android 12]
- **Min SDK Version**: [e.g., 24]
- **Target SDK Version**: [e.g., 35]
- **Device/Emulator**: [e.g., Pixel 5, Android Emulator]
- **Player**: [e.g., ExoPlayer 2.19.0, VideoView, etc.]
- **Kotlin Version**: [e.g., 2.0.21]

## Code Sample
```kotlin
// Please provide a minimal code sample that reproduces the issue
```

## Logs/Stack Trace
```
Paste relevant logs or stack traces here
```

## Additional Context
Add any other context about the problem here.

## Screenshots
If applicable, add screenshots to help explain your problem.

