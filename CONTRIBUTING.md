# Contributing to Personal App

Thank you for your interest in contributing! This document provides guidelines and instructions for contributing to the Personal App project.

## 🚀 Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/personal-app.git`
3. Create a branch: `git checkout -b feature/your-feature-name`
4. Make your changes
5. Commit with meaningful messages
6. Push to your fork: `git push -u origin feature/your-feature-name`
7. Create a Pull Request

## 📝 Code Style Guidelines

### Kotlin Style
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use meaningful variable and function names

### Example:
```kotlin
// Good
fun fetchWeatherData(latitude: Double, longitude: Double): WeatherData

// Bad
fun getW(lat: Double, lon: Double): W
```

### Architecture
- Follow **MVVM (Model-View-ViewModel)** pattern
- Keep Activities/Fragments lean - move logic to ViewModels
- Use LiveData or StateFlow for state management
- Repository pattern for data access

## 🔀 Branch Naming Convention

Use descriptive branch names with prefixes:

- `feature/` - New features (e.g., `feature/add-dark-mode`)
- `fix/` - Bug fixes (e.g., `fix/weather-api-error`)
- `docs/` - Documentation updates (e.g., `docs/update-readme`)
- `refactor/` - Code refactoring (e.g., `refactor/cleanup-code`)
- `chore/` - Maintenance tasks (e.g., `chore/update-dependencies`)

## 💬 Commit Message Format

Use [Conventional Commits](https://www.conventionalcommits.org/) format:

```
<type>: <subject>

[optional body]

[optional footer]
```

### Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

### Examples:
```
feat: Add dark mode support to terminal UI

fix: Resolve ChatGPT API timeout issue

docs: Update README with installation steps
```

## 🔍 Pull Request Process

1. **Update Documentation**: If you're adding features, update relevant docs
2. **Write Tests**: Add tests for new features if applicable
3. **Follow PR Template**: Fill out the PR template completely
4. **Keep PRs Focused**: One feature/fix per PR
5. **Test Your Changes**: Build and test on a device or emulator
6. **Request Review**: Tag relevant reviewers

### PR Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings
- [ ] Tests pass (if applicable)
- [ ] Builds successfully

## 🧪 Testing

- Test on physical devices when possible
- Test on emulators with different Android versions
- Verify all features work as expected
- Check for crashes and errors

## 📚 Documentation

- Update README.md for new features
- Add code comments for complex logic
- Update this CONTRIBUTING.md if needed
- Keep API documentation up to date

## ❓ Questions?

- Open an issue for questions or discussions
- Check existing issues before creating new ones
- Be respectful and constructive in discussions

Thank you for contributing! 🎉

