import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)

}

group = "com.example.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    runtimeOnly(libs.android.native.lint)
    runtimeOnly(libs.ktlint)
    //implementation(libs.bundles.detekt) //compileOnly
    implementation(libs.detekt)
    //implementation(libs.detekt.compose)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidLint") {
            id = "com.example.convention.android.lint"
            implementationClass = "com.example.buildlogic.AndroidLintConventionPlugin"
        }
    }
}