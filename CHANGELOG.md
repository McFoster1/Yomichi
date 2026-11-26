# Changelog

## Yomichi (fork of Novery 1.0.2)

### Added
- Streak freeze: freeze today's streak from the Reading Stats screen, once per week (resets Monday)
- Select all chapters above or below the selected ones in chapter selection mode
- "Choose" in the download menu: download from chapter X to chapter Y
- Add your own providers from Settings > Providers, with edit and delete
- Select all chapters above or below the selected ones, with two buttons in chapter selection mode
- "Choose" in the download menu to download a range of chapters (from chapter X to chapter Y)
- Streak freeze: freeze today's reading streak once per week from the Reading Stats screen
- Copy crash log button in About
- Releases built by a manually run GitHub Actions workflow with a chosen version number, signed with the Yomichi key

### Changed
- New default color theme: midnight background with a soft cyan accent. The previous orange look is still available as the "Classic" preset in Settings
- Renamed to Yomichi with a new icon, system splash, and "BY MCFOSTER" splash screen
- Accent colors in the reader bar, TTS player, voice selector, and chapter list now follow the app theme
- About page credits the original Novery project and notes that Yomichi is a fork
- Backup files are now named `yomichi_backup_...`
- Settings > About shows the real app version

### Fixed
- The last chapter of each page (for example chapter 50) was missing in paged chapter lists
- Closing a chapter no longer sends the chapter list back to page 1
- Chapter list now stays on the current page after reading a chapter, instead of going back to page 1
- The last chapter of each page (for example chapter 50 or 150) was missing in page mode
- Text selection now works across any amount of text without crashing
- FreeWebNovel: search, covers, full chapter list, and a duplicated chapter title
- LibRead: chapter list stopped at 40 chapters
- NovelFire: chapters failed to load, and titles now show as "Chapter N: Title" for the whole list
- Update checker no longer shows an error when no release exists yet

---

## Novery (original project)

## [1.0.0] — 2025-02-14

### Initial Release

- Browse and search novels from 6 sources
- Customizable reader with continuous scroll and paged modes
- Text-to-Speech with background playback and media controls
- Download chapters for offline reading
- Library management with reading status tracking
- Personalized recommendations
- Material You theming with custom color support
- Reading statistics and history tracking
- Backup and restore (including QuickNovel import)
