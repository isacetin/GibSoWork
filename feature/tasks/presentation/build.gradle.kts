plugins {
    alias(libs.plugins.gib.android.library)
    alias(libs.plugins.gib.android.compose)
    alias(libs.plugins.gib.android.hilt)
}

android {
    namespace = "com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation"
}

dependencies {
    implementation(project(":feature:tasks:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:di"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}
