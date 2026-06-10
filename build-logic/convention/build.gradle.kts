plugins {
    `kotlin-dsl`
}

group = "com.isacetin.gibinteraktifsosyalapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.compose.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "gib.android.library"
            implementationClass = "GibAndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "gib.android.application"
            implementationClass = "GibAndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "gib.android.compose"
            implementationClass = "GibAndroidComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "gib.android.hilt"
            implementationClass = "GibAndroidHiltConventionPlugin"
        }
        register("jvmDomain") {
            id = "gib.jvm.domain"
            implementationClass = "GibJvmDomainConventionPlugin"
        }
    }
}
