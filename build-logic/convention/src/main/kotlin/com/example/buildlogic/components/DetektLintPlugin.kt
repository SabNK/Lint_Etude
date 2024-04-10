package com.example.buildlogic.components

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

class DetektLintPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("detekt").asString())
            val extension = extensions.getByType<DetektExtension>()
            when {
                isAnroidApplication() -> configureDetektAndroidApplication(extension)
                isAnroidLibrary() -> configureDetektAndroidLibrary(extension)
                else -> configureDetektLibrary(extension)
            }

            // Configure jvmTarget for gradle task `detekt`
            tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }

            // Configure jvmTarget for gradle task `detektGenerateBaseline`
            tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
}

private fun Project.configureDetektAndroidApplication(extension: DetektExtension) {
    configureDetekt(extension)
}

private fun Project.configureDetektAndroidLibrary(extension: DetektExtension) {
    configureDetekt(extension)
}

private fun Project.configureDetektLibrary(extension: DetektExtension) {
    configureDetekt(extension)
}