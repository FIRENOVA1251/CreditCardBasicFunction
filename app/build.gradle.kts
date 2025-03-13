plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt") // ➜ 新增這一行來啟用 KAPT
    id("dagger.hilt.android.plugin") // ➜ 確保 Hilt 插件也被啟用
}


android {
    namespace = "com.example.nitracard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nitracard"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Jetpack Compose (Material 3)
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.8")
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.8")
    implementation("androidx.navigation:navigation-compose:2.8.8")
    implementation("androidx.compose.material:material-icons-extended:1.6.7") // extra icon
    implementation("com.google.accompanist:accompanist-navigation-animation:0.33.2-alpha")


    // ViewModel + LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-compiler:2.46.1")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    // Testing
    testImplementation(libs.junit)
    // Kotlin 协程测试 (用于模拟 suspend functions)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Mockito 核心库 (用于 Mock 依赖)
    testImplementation("org.mockito:mockito-core:5.4.0")
    // Mockito Kotlin 扩展库 (简化 Kotlin 测试)
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    // Mockito 用于 Android Instrumented Tests
    androidTestImplementation("org.mockito:mockito-android:5.4.0")
    // Google Truth (用于更清晰的断言)
    testImplementation("com.google.truth:truth:1.1.3")
    // Mockk (可选, 更适合 Kotlin 的 Mock 框架)
    testImplementation("io.mockk:mockk:1.13.7")
    // AndroidX 测试库 (提供 LiveData & Flow 的测试工具)
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose UI Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
    // Espresso UI Test
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")

    // AndroidX Test
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")



}