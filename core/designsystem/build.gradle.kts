plugins {
    alias(libs.plugins.gib.android.library)
    alias(libs.plugins.gib.android.compose)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material.icons.extended)
}
