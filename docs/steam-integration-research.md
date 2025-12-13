# Steam Integration Research

## Executive Summary

**Feasibility: HIGH** - All components exist and have been proven to work on Android.

The key discovery is **Pluvia** ([github.com/oxters168/Pluvia](https://github.com/oxters168/Pluvia)) - an open-source GPL3 Android app that already implements:
- Steam QR code login
- Owned games library browsing
- Game downloading via Steam CDN
- Steam Cloud save sync
- x86 game execution via embedded Winlator

We could either:
1. **Integrate Pluvia's approach** - Use JavaSteam + embedded Winlator
2. **Fork/collaborate with Pluvia** - They've solved all the hard problems
3. **Build from scratch** - Use the same libraries they use

---

## Component Breakdown

### 1. Authentication (QR Code Login)

**Solution: JavaSteam library** (`in.dragonbra:javasteam`)

Steam's IAuthenticationService API supports QR-based login:
- `BeginAuthSessionViaQR` - Initiates auth, returns challenge URL for QR code
- `PollAuthSessionStatus` - Polls until user scans QR from Steam mobile app
- Returns access token + refresh token for session persistence

**Pluvia implementation** (SteamService.kt:819-905):
```kotlin
val authSession = steamClient.authentication.beginAuthSessionViaQR(authDetails).await()
// authSession.challengeUrl contains the QR code data
val pollResult = authSession.pollAuthSessionStatus().await()
// pollResult contains accessToken, refreshToken, accountName
```

**Key points:**
- QR code refreshes every 30 seconds
- User scans with official Steam mobile app
- No password entry required on device
- Refresh tokens persist login across sessions

---

### 2. Game Library (Owned Games)

**Solution: JavaSteam + PICS (Package Info Cache System)**

Steam doesn't use a simple "get owned games" API. Instead:
1. Receive `LicenseListCallback` with all owned package IDs
2. Query PICS for package details (which apps each package contains)
3. Query PICS for app details (name, depots, launch configs)

**Pluvia stores locally:**
- `SteamLicense` - Package ownership info
- `SteamApp` - Game metadata (name, depots, install directories)

**Key classes:**
- `SteamApps.picsGetProductInfo()` - Fetch app/package metadata
- `PICSProductInfoCallback` - Returns KeyValue data about games

---

### 3. Game Downloads

**Solution: JavaSteam ContentDownloader**

JavaSteam includes a `ContentDownloader` class that handles:
- Depot manifest fetching
- CDN server selection
- Chunk downloading with verification
- Delta patching for updates

**Pluvia implementation** (SteamService.kt:465-517):
```kotlin
ContentDownloader(steamClient).downloadApp(
    appId = appId,
    depotId = depotId,
    installPath = installPath,
    stagingPath = stagingPath,
    branch = "public",
    onDownloadProgress = { progress -> ... }
)
```

**Storage considerations:**
- Games install to external storage (`/storage/emulated/0/Pluvia/Steam/steamapps/common/`)
- Depot manifests cached for update detection
- Only Windows x64 depots are downloaded (filtered by OS/arch)

---

### 4. x86 Translation Layer

**Solution: Winlator (embedded)**

Winlator is the de-facto standard for running Windows games on Android. It combines:
- **Wine** - Windows API compatibility layer
- **Box64** - x86_64 to ARM64 binary translation
- **DXVK/VKD3D** - DirectX to Vulkan translation

**Pluvia's approach:**
- Winlator is embedded directly in the app (not a separate APK)
- Ubuntu filesystem image (imagefs.txz) provides Linux environment
- Games run in isolated "containers" with configurable settings

**Performance notes:**
- Best on Snapdragon chips (Adreno GPUs)
- Games before 2013 / DX9 work best
- 10-20% overhead from translation
- Modern games (DX11/12) are playable but slower

---

### 5. Steam Cloud Saves

**Solution: JavaSteam SteamCloud handler**

Pluvia implements full Steam Cloud sync:
- `SteamCloud.signalAppLaunchIntent()` - Notify Steam of game launch
- `SteamAutoCloud.syncUserFiles()` - Upload/download save files
- `SteamCloud.signalAppExitSyncDone()` - Notify Steam of game exit

This allows saves to sync between Android and PC/Steam Deck.

---

## Libraries Required

| Library | Purpose | Maven/Gradle |
|---------|---------|--------------|
| JavaSteam | Steam network protocol | `in.dragonbra:javasteam` |
| Winlator | Windows game execution | Embedded (forked source) |
| BouncyCastle | Cryptography for Steam | `org.bouncycastle:*` |
| Wire | Protobuf serialization | `com.squareup.wire:*` |
| Ktor | HTTP client | `io.ktor:*` |

---

## Architecture Options

### Option A: Minimal Integration (Recommended Start)
1. Add Steam login (QR code) to Argosy
2. Fetch and display owned games alongside RomM games
3. Download games to device storage
4. Launch games via external Winlator APK (user installs separately)

**Pros:** Simpler, smaller APK, users can update Winlator independently
**Cons:** Requires user to install Winlator, less seamless experience

### Option B: Full Integration (Pluvia-style)
1. Embed Winlator directly in Argosy
2. Handle all translation layer setup internally
3. Provide container management UI

**Pros:** Seamless experience, everything in one app
**Cons:** Massive APK size (100MB+), complex maintenance, duplicated effort

### Option C: Collaborate with Pluvia
1. Contribute improvements to Pluvia
2. Integrate Pluvia as a "Steam backend" module
3. Share UI components and game management

**Pros:** Leverage existing work, community support
**Cons:** Less control, GPL3 license considerations

---

## Technical Challenges

### 1. APK Size
- Winlator + imagefs = 200MB+
- Consider dynamic feature modules (Play Feature Delivery)

### 2. Storage Permissions
- Steam games can be 10-50GB each
- Need robust storage access (SAF or MANAGE_EXTERNAL_STORAGE)

### 3. Performance Tuning
- Each game may need specific Box64/Wine settings
- Container configuration UI required

### 4. DRM Limitations
- Only DRM-free Steam games work
- Steam DRM (CEG) requires actual Steam client
- Most indie games work, AAA titles often don't

### 5. Legal Considerations
- Steam ToS allows personal use
- No distribution of Steam binaries
- Wine/Box64 are clean-room implementations

---

## Recommended Next Steps

1. **Prototype Steam login** - Add QR auth using JavaSteam
2. **Display owned games** - Show Steam library in Argosy UI
3. **Basic download** - Download a simple game to device
4. **External Winlator** - Launch via intent to installed Winlator
5. **Evaluate embedded approach** - If external works, consider embedding

---

## References

- [Pluvia Source Code](https://github.com/oxters168/Pluvia)
- [JavaSteam](https://github.com/Longi94/JavaSteam)
- [Winlator](https://github.com/brunodev85/winlator)
- [Steam Web API Docs](https://steamapi.xpaw.me/)
- [SteamKit2 (.NET reference)](https://github.com/SteamRE/SteamKit)
- [kSteam (Kotlin alternative)](https://github.com/iTaysonLab/kSteam)
