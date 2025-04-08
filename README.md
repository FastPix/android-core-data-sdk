# Introduction:
The FastPix Android Data Core SDK serves as the foundational layer for integrating video playback analytics within Android applications. As a core SDK, it does not function as a standalone player but instead enhances any video player by capturing essential playback metrics such as playback events, buffering patterns, and user engagement data. This data is automatically collected and made available on the FastPix dashboard for real-time monitoring and analysis. Designed for efficiency, the SDK ensures minimal impact on playback performance. While currently optimized for Java-based Android projects, future updates will introduce extended support for Kotlin and enhanced customization options for analytics tracking.

# Key Features:
- **Automatic analytics tracking** – Seamlessly captures playback metrics without additional configuration.  
- **Real-time insights** – Provides instant access to video performance data on the FastPix dashboard.  
- **Optimized for performance** – Ensures minimal impact on playback and system resources.  
- **Future enhancements** – Planned support for Kotlin and expanded analytics customization options.  

# Prerequisites:
- Android Studio Arctic Fox or later
- Minimum Android SDK 21+
- Sample Player integrated into your project
- FastPix Data Core SDK added as a dependency
- Generate a GitHub Personal Access Token (PAT) from Your GitHub account

# Installation:
Add our maven repository to your **settings.gradle**:
```groovy
repositories {
  maven {
    url = uri("https://maven.pkg.github.com/FastPix/android-data-core-sdk")
    credentials {
        username = "Github_User_Name"  // Your Github account user name 
        password = "Github_PAT" // Your (PAT) Personal access token Get It from you Github account 
    }
  }
}
```
Add the FastPix Data Core SDK dependencie to your **build.gradle**:
```gradle
dependencies {
    implementation 'io.fastpix.data:core-sdk:1.0.0'
}
```

#Basic Usage: 

## Initialize `ExamplePlayer`
To begin, initialize the player of your choice. In this example, we use `ExamplePlayer`, but you can replace it with **any supported Android player**.

```java
// Inside your activity or fragment
Context context = this;
ExamplePlayer examplePlayer = new ExamplePlayer.Builder(context).build(); // You can use any android supported player..
CustomerDataEntity customerDataEntity = new CustomerDataEntity(); // Initialize with necessary data
CustomOptions options = new CustomOptions();   // Define custom options

TestBaseSamplePlayer testBaseSamplePlayer = new TestBaseSamplePlayer(context, examplePlayer, customerDataEntity, options);
```

### 2. Add Analytics Listener
To track playback state changes, attach an analytics listener:

```java
examplePlayer.addAnalyticsListener(new AnalyticsListener() {
    @Override
    public void onPlaybackStateChanged(int state) {
        handleSamplePlayerState(state, player.getPlayWhenReady());
    }
});
```

### 3. Handle Sample Player Playback State
Implement a method to handle different playback states:
```java
public void handleSamplePlayerState(int playbackState, boolean playWhenReady) {
    switch (playbackState) {
        case Player.STATE_IDLE:
            Log.d("Test", "Player is idle");
            break;
        case Player.STATE_BUFFERING:
            Log.d("Test", "Buffering...");
            break;
        case Player.STATE_READY:
            Log.d("Test", "Playback Ready");
            break;
        case Player.STATE_ENDED:
            Log.d("Test", "Playback Ended");
            break;
        default:
            Log.d("Test", "Unknown State");
    }
}
```

# Detailed Usage:
- Customize buffering settings via `CustomOptions`.
- Integrate Fastpix analytics for detailed performance tracking.
- Implement error handling for better user experience.
