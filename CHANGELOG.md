# Changelog

All notable changes to this project will be documented in this file.

## [1.2.7]
## Changed
- Replaced the manual event cleanup logic with a Room-based persistence layer and WorkManager-driven upload pipeline.
- Added `AnalyticsDatabase`, `EventStore`, and associated DAOs/Entities to handle persistent event storage.
- Introduced `EventQueue` for thread-safe in-memory event buffering.
- Implemented `EventUploadWorker` and `EventUploadScheduler` to manage reliable background data synchronization.
- Updated `FastPixAnalytics` and `FastPixDataSDK` to use a new lifecycle-aware state machine (`SdkLifecycleState`).
- Refactored `EventDispatcher` to orchestrate the flow from the in-memory queue to the persistent store and network.
- Updated the default beacon domain from `metrix.ws` to `anlytix.io`.
- Enhanced the `Logger` utility with structured formatting, session/trace tracking, and standardized event labels.
- Updated dependencies for Room, KSP, and Kotlin serialization.

## [1.2.6]
### Improved
- **SDK Version Update**:
- Crash Fix
- Bug Fix

## [1.2.5]
### Improved
- **SDK Version Update**:
- SDK Version Update
- Bug Fix

## [1.2.4]
### Improved
- **SDK Version Update**:
- SDK Version Update

## [1.2.3]
### Improved
- **SDK Version Update**:
- SDK Version Update
- Improve Analytics
- Improved Data

## [1.2.2]
### Improved
- **SDK Version Update**:
- SDK Version Update

## [1.2.1]
### Improved
- **Major Code Optimization and Refactoring**:
- Data Syncing to server by keeping work manager
- Handles app killing scenario also
- Keeps the data in local and removes it after synced to server

## [1.2.0]
### Improved
- **Major Code Optimization and Refactoring**:
- Resolved crash in network monitoring caused by repeated registerNetworkCallback calls
- Ensured network callback lifecycle is safely handled
- Improved stability when tracking connectivity events

## [1.1.0]
### Changed
- **Major Code Optimization and Refactoring**:
- Comprehensive code refactoring for improved maintainability and performance.
- Optimized internal components and dependencies for better efficiency.
- Enhanced code structure and organization across the SDK.
- Improved overall stability and reduced technical debt.

## [1.0.2]
### Added
- **Enhanced Playback Experience and Stability Improvements**:
- Fullscreen Detection: Tracked views only after fullscreen activation.
- Request Event Logging: Enhanced cancellation details and error tracking.
- Playback Stability: Fixed event transitions, seek handling, and playhead updates.
- SDK Version Tracking: Included FastPix SDK name and version in metadata.

## [1.0.1]
### Added
- **Update core files**
  -logic and code updated

## [1.0.0]
### Added
- **Integration with ExoPlayer**:
  - Enabled video performance tracking using FastPix Data SDK, supporting ExoPlayer streams with user engagement metrics, playback quality monitoring, and real-time diagnostics.
  - Provides robust error management and reporting capabilities for seamless ExoPlayer video performance tracking.
  - Allows customizable behavior, including options to disable data collection, respect Do Not Track settings, and configure advanced error tracking with automatic error handling.
  - Includes support for custom metadata, enabling users to pass optional fields such as video_id, video_title, video_duration, and more.
  - Introduced event tracking for onPlayerStateChanged and onTracksChanged to handle seamless metadata updates during playback transitions.
    