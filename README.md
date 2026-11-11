<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/images/logo-dark.png">
    <img src="docs/images/logo-light.png" alt="Yomichi logo" width="160">
  </picture>
</p>

<h1 align="center">Yomichi</h1>

<div align="center">

**A modern Android novel reader with multi-source support, TTS, and offline reading**

[![Release](https://img.shields.io/github/v/release/McFoster1/Yomichi?label=Release)](../../releases/latest)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-26-blue.svg)](https://developer.android.com/about/versions/oreo)
[![License](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](LICENSE)

[Download](#-installation) · [Features](#-features) · [Credits](#-credits)

</div>

---

## 📖 About

**Yomichi** is a free, open-source Android app for reading web novels from multiple sources, with **Text-to-Speech**, **offline reading**, and **personalized recommendations**. It is built with **Jetpack Compose** and **Material 3**.

Yomichi is a fork of [Novery](https://github.com/1Finn2me/Novery) by 1Finn2me, continued and maintained by **McFoster**. The name comes from Tachiyomi and Mihon, the apps that inspired this style of reader.

---

## ✨ Features

### 📚 Multi-Source Browse & Search
- **10 built-in sources**: NovelFire, WtrLab, NovelBin, LibRead, RoyalRoad, NovelsOnline, Lnori, Webnovel, FreeWebNovel, AllNovel
- **Add your own source** from *Settings > Providers* using a site's address and CSS selectors, and edit or delete it later
- Progressive streaming search with fuzzy matching and history
- Enable, disable, and reorder individual sources

### 📖 Reader
- Continuous scroll or Chaptered view
- Customizable fonts, sizing, spacing, alignment, and colors
- Fullscreen mode with auto-hiding controls and volume key navigation
- Text selection across any amount of text
- Bookmarks and stable position tracking across sessions

### 🎧 Text-to-Speech
- Background playback with the screen off
- Real-time sentence highlighting and auto-scroll
- Lock screen and Bluetooth media controls
- Automatic chapter advancement and full voice selection

### 💾 Offline Reading
- Background download queue with priority levels
- Auto-download new chapters from library novels
- Per-novel storage management

### 📚 Library
- Track status: Reading, Completed, On Hold, Plan to Read, Dropped
- New chapter detection with badge indicators
- Filtering, sorting, and batch operations

### 🤖 Recommendations
- Tag-based matching that learns from your reading patterns
- Categories: *For You*, *Because You Read X*, *From Authors You Like*
- Configurable filters to block unwanted tags, authors, or sources

### 🎨 Theming
- Material You dynamic colors (Android 12+)
- Light, Dark, and AMOLED black modes
- Preset themes plus a full custom color picker
- Independent reader color scheme

### 📊 Statistics, History & Backup
- Automatic reading time tracking and daily streaks
- History timeline grouped by date
- Backup and restore, including Novery backups
- A built-in **crash log** you can copy from *About* when reporting a bug

---

## 📥 Installation

### Download
1. Grab the latest APK from [Releases](../../releases/latest)
2. Install it and grant notification permission when prompted

> Requires **Android 8.0+ (API 26)**

### Build from Source
Releases are built by GitHub Actions: open the **Actions** tab, choose **Build and release Yomichi**, press **Run workflow**, and enter a version number. The signed APK is published on the Releases page.

To build locally:
```bash
git clone https://github.com/McFoster1/Yomichi.git
cd Yomichi
./gradlew assembleDebug
```
Requires **JDK 17** and **Kotlin 2.0.21**.

---

## 🙏 Credits

- [Novery](https://github.com/1Finn2me/Novery) by 1Finn2me, the project Yomichi is forked from
- [Tachiyomi](https://github.com/tachiyomiorg/tachiyomi) and [Mihon](https://github.com/mihonapp/mihon), which pioneered the multi-source reader concept
- [QuickNovel](https://github.com/LagradOst/QuickNovel), inspiration for novel-specific reader design
- [LNReader](https://github.com/LNReader/lnreader-sources), extensible source architecture

Yomichi is released under the **GNU General Public License v3.0**. See [LICENSE](LICENSE). The original copyright notices are kept, and the changes made in this fork are recorded in the commit history and in the [changelog](CHANGELOG.md).

---

## ⚠️ Disclaimer

Yomichi does not host, store, or distribute any content. The app functions as a search engine and aggregator: it crawls and displays content from third-party websites that are publicly accessible through any standard web browser. Yomichi has no affiliation with, and no control over, the content provided by these sources.

Any legal concerns regarding content should be directed to the respective website operators and content hosts. In cases of copyright infringement, please contact the responsible parties or file hosts directly.

This application is intended for personal and educational use only. Users are solely responsible for ensuring their use of the app complies with all applicable local, national, and international laws. Use Yomichi at your own risk.

By using this application, you acknowledge that the developers of Yomichi bear no responsibility for any content accessed through third-party sources, nor for any consequences arising from the use of this app.

---

<div align="center">

⭐ **Star the repo if you find Yomichi useful!**

[Report Bug](../../issues) · [Request Feature](../../issues)

</div>
