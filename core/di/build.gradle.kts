plugins {
    alias(libs.plugins.gib.android.library)
    alias(libs.plugins.gib.android.hilt)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.core.di"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
