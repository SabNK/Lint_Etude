package com.example.buildlogic.components


import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class SpotlessLintPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.findPlugin("spotless").asString())
            val extension = extensions.getByType<SpotlessExtension>()
            when {
                isAnroidApplication() -> configureSpotlessAndroidApplication(extension)
                isAnroidLibrary() -> configureSpotlessAndroidLibrary(extension)
                else -> configureSpotlessLibrary(extension)
            }
        }
    }
}

private fun Project.configureSpotlessLibrary(extension: SpotlessExtension) {
    configureSpotless(extension)
}

private fun Project.configureSpotlessAndroidLibrary(extension: SpotlessExtension) {
    configureSpotless(extension)
}

private fun Project.configureSpotlessAndroidApplication(extension: SpotlessExtension) {
    this.configureSpotless(extension)
}

private fun Project.configureSpotless(extension: SpotlessExtension) = extension.apply{
    java {
        target("**/*.java")
        googleJavaFormat().aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
    kotlin {
        target("**./*.kt")
        trimTrailingWhitespace()
        ktlint()
        indentWithSpaces()
        endWithNewline()
    }
}