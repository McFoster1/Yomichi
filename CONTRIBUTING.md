# Contributing to Yomichi

Thanks for your interest in contributing!

## Getting Started

1. Fork the repository (click the Fork button on the top right)
2. Make your changes
3. Open a Pull Request

## Guidelines

- Follow the existing code style
- Test on a real device or emulator before submitting
- Keep changes focused: one feature or fix per PR

## Adding a Novel Source

For a simple site, you can add it inside the app without writing code: open *Settings > Providers > Add provider* and enter the site's address and CSS selectors.

For a full source written in code:

1. Create a class extending `MainProvider`
2. Implement: `search()`, `loadMainPage()`, `load()`, `loadChapterContent()`
3. Register it in `NoveryApp.registerProviders()`
4. Test with various novels

## Reporting Bugs

Use the Bug Report template when creating an issue. Include your app version, Android version, and steps to reproduce. If the app crashed, open *About* and use **Copy crash log**, then paste it into the issue.
