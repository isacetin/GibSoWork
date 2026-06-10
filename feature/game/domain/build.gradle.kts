plugins {
    alias(libs.plugins.gib.jvm.domain)
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
