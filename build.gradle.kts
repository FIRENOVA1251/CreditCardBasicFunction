// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}

// 根目錄 build.gradle.kts (Project-level)
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
    }
}
