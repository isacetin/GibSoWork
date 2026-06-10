plugins {
    alias(libs.plugins.gib.android.application)
    alias(libs.plugins.gib.android.compose)
    alias(libs.plugins.gib.android.hilt)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp"

    defaultConfig {
        applicationId = "com.isacetin.gibinteraktifsosyalapp"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:di"))

    implementation(project(":feature:tasks:domain"))
    implementation(project(":feature:tasks:data"))
    implementation(project(":feature:tasks:presentation"))

    implementation(project(":feature:shop:domain"))
    implementation(project(":feature:shop:data"))
    implementation(project(":feature:shop:presentation"))

    implementation(project(":feature:events:domain"))
    implementation(project(":feature:events:data"))
    implementation(project(":feature:events:presentation"))

    implementation(project(":feature:game:domain"))
    implementation(project(":feature:game:data"))
    implementation(project(":feature:game:presentation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
