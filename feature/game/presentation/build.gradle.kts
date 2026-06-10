plugins {
    alias(libs.plugins.gib.android.library)
    alias(libs.plugins.gib.android.compose)
    alias(libs.plugins.gib.android.hilt)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.feature.game.presentation"
}

dependencies {
    implementation(project(":feature:game:domain"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)
}
