package com.example.nitracard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * **Application Class for Dependency Injection**
 *
 * - This class serves as the **entry point** for the application.
 * - Annotated with `@HiltAndroidApp`, which triggers **Hilt's code generation**.
 * - Automatically initializes **Hilt dependency injection** across the entire app.
 *
 * - Required for using **Dagger Hilt** in an Android application.
 * - Ensures that all components (ViewModels, Repositories, etc.) can be injected properly.
 *
 * **Usage:**
 * - This class must be declared in the `AndroidManifest.xml` as:
 *   ```xml
 *   <application
 *       android:name=".MyApplication"
 *       ... >
 *   </application>
 *   ```
 */
@HiltAndroidApp
class MyApplication : Application()
