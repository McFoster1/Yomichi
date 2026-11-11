# Changelog

## Yomichi (fork of Novery 1.0.2)

### Added
- Add your own providers from Settings > Providers, with edit and delete
- Copy crash log button in About
- Releases built by a manually run GitHub Actions workflow with a chosen version number, signed with the Yomichi key

### Changed
- Renamed to Yomichi with a new icon, system splash, and "BY MCFOSTER" splash screen
- Accent colors in the reader bar, TTS player, voice selector, and chapter list now follow the app theme
- About page credits the original Novery project and notes that Yomichi is a fork
- Backup files are now named `yomichi_backup_...`
- Settings > About shows the real app version

### Fixed
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
