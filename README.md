# 🍽️ Plateful — Meal Planner App

A full-featured Android meal planning app built with **Jetpack Compose** and **Clean Architecture**. Browse thousands of recipes, plan your weekly meals, save your favorites, and manage your profile — all with a clean, modern UI and dark mode support.

---

## 📱 Screenshots

<table>
  <tr>
    <td align="center"><b>Splash screen</b></td>
    <td align="center"><b>Home</b></td>
    <td align="center"><b>Profile</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6470a9a4-ea80-42b3-821d-c26487fd4e62" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/bc06b8d6-c46f-40ea-a821-ff21fbcab650" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/c97680e8-71b4-4c53-8c81-c470fc564784" width="220"/></td>
  </tr>
  <tr>
    <td align="center"><b>Planner</b></td>
    <td align="center"><b>Search</b></td>
    <td align="center"><b>Favorites</b></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/f2ccaf9a-f4bc-4ae8-b55c-b31b823050c4" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/a542adac-6f14-4948-94f2-4fb916fdbdf0" width="220"/></td>
    <td><img src="https://github.com/user-attachments/assets/06f3d6e7-b2a7-4f0a-af90-6472d1137aa8" width="220"/></td>
  </tr>
</table>

---

## ✨ Features

- 🔐 **Authentication** — Email/password and Google Sign-In via Firebase Auth, with email verification on signup
- 🏠 **Home** — Browse meals by category with a search bar for quick access
- 🔍 **Search** — Search recipes by name, country, category, or ingredient
- 🍲 **Meal Details** — Full recipe view with ingredients, step-by-step instructions, and a YouTube tutorial player
- ❤️ **Favorites** — Save and manage your favorite meals, persisted locally with Room
- 📅 **Weekly Meal Planner** — Plan meals day by day with a week calendar view, swipe to delete planned meals
- 👤 **Profile** — Edit display name, pick a profile photo from gallery, and toggle dark mode
- 🌙 **Dark Mode** — Per-user dark mode preference saved with DataStore, persists across sessions
- 🎬 **Splash Screen** — Animated splash screen with auth-based routing (skips login if already signed in)

---

## 🏗️ Architecture

This app follows **Clean Architecture** with 3 clear layers:

```
app/
├── data/               # Data sources, Room DB, Retrofit, Repositories impl
│   ├── local/          # Room database, DAOs, DataStore, ProfileImageManager
│   ├── remote/         # Retrofit API service, Google Auth client
│   ├── mapper/         # Data ↔ Domain model mappers
│   └── repo/           # Repository implementations
├── domain/             # Business logic (no Android dependencies)
│   ├── entity/         # Domain models (Meal, Category, etc.)
│   ├── repo/           # Repository interfaces
│   └── usecase/        # One use case per action
├── ui/                 # Jetpack Compose UI
│   ├── screens/        # All screens
│   ├── components/     # Reusable composables
│   ├── viewmodel/      # ViewModels per screen
│   └── theme/          # Material 3 theme, colors
├── di/                 # Hilt modules (AppModule, RepoModule)
└── navigation/         # NavGraph, NavigationItem
```

---

## 🛠️ Tech Stack

| Category | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| DI | Hilt |
| Navigation | Navigation Compose |
| Networking | Retrofit + OkHttp + Gson |
| Local DB | Room |
| Preferences | DataStore |
| Auth | Firebase Auth (Email + Google) |
| Image Loading | Coil |
| Video | Android YouTube Player |
| Async | Kotlin Coroutines + Flow |

---

## 🌐 API

This app uses the free [TheMealDB API](https://www.themealdb.com/api.php) to fetch:
- Meal categories
- Meals by category, name, area, ingredient, or ID
- Ingredient images

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- A Firebase project with **Authentication** enabled (Email/Password + Google)

### Setup

1. **Clone the repo**
```bash
git clone https://github.com/yourusername/plateful.git
cd plateful
```

2. **Add Firebase config**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Download `google-services.json`
   - Place it in `app/`

3. **Google Sign-In setup**
   - Get your SHA-1 fingerprint:
   ```bash
   ./gradlew signingReport
   ```
   - Add it to Firebase → Project Settings → Your App
   - Re-download `google-services.json`

4. **Build and run**
   - Open in Android Studio
   - Sync Gradle
   - Run on emulator or device (API 26+)

---

## 📦 Module Dependencies

```
ViewModel → UseCase → Repository Interface → Repository Impl → Data Sources
```

ViewModels never depend directly on data sources — all access goes through use cases and repository interfaces.

---

## 🔮 Future Improvements

- [ ] Push notifications for planned meals
- [ ] Login/Sign up via Facebook
- [ ] Cloud sync for favorites and plans
- [ ] Widget for today's planned meal

---

## 📄 License

```
MIT License — feel free to use, modify, and distribute.
```

---

> Built with ❤️ using Jetpack Compose
