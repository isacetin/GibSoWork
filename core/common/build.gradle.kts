plugins {
    alias(libs.plugins.gib.android.library)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
