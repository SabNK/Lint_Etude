import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)

}

group = "com.example.buildlogic"
println(":convention projectDir $projectDir")
println(":convention rootDir $rootDir")

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
    //compileOnly(libs.android.tools.common)
    //compileOnly(libs.kotlin.gradlePlugin)
    //runtimeOnly(libs.android.native.lint) //check with NIA
    runtimeOnly(libs.ktlint)
    //implementation(libs.bundles.detekt) //compileOnly
    implementation(libs.detekt)
    implementation(libs.spotless)
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
        register("androidCodeCoverage") {
            id = "com.example.convention.android.code.coverage"
            implementationClass = "com.example.buildlogic.AndroidJacocoConventionPlugin"
        }


        //components supportive plugins
        register("detektLint") {
            id = "com.example.buildlogic.detekt.lint"
            implementationClass = "com.example.buildlogic.components.DetektLintPlugin"
        }
        register("spotlessLint") {
            id = "com.example.buildlogic.spotless.lint"
            implementationClass = "com.example.buildlogic.components.SpotlessLintPlugin"
        }
    }
}