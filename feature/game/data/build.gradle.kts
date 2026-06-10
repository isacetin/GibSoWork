plugins {
    alias(libs.plugins.gib.android.library)
    alias(libs.plugins.gib.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.feature.game.data"
}

dependencies {
    implementation(project(":feature:game:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(libs.kotlinx.serialization.json)
}
