plugins {
    `kotlin-dsl`
}

group = "com.isacetin.gibinteraktifsosyalapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
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
