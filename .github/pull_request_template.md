# FastPix Resumable Uploads SDK - Documentation PR

## Documentation Changes

### What Changed
- [ ] New documentation added
- [ ] Existing documentation updated
- [ ] Documentation errors fixed
- [ ] Code examples updated
- [ ] Links and references updated

### Files Modified
- [ ] README.md
- [ ] docs/ files
- [ ] USAGE.md
- [ ] CONTRIBUTING.md
- [ ] Other: _______________

### Summary
**Brief description of changes:**

<!-- What documentation was added, updated, or fixed? -->

### Code Examples
```kotlin 
// If you added/updated code examples, include them here
    private val fastPixSDK = FastPixDataSDK()
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

### Testing
- [ ] All code examples tested
- [ ] Links verified
- [ ] Grammar checked
- [ ] Formatting consistent

### Review Checklist
- [ ] Content is accurate
- [ ] Code examples work
- [ ] Links are working
- [ ] Grammar is correct
- [ ] Formatting is consistent

---

**Ready for review!**
