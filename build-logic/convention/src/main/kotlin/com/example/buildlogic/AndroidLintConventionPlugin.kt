package com.example.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.example.buildlogic.components.asString
import com.example.buildlogic.components.configureDetekt
import com.example.buildlogic.components.libs
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

private fun Lint.configure() {
    xmlReport = true
    checkDependencies = true
}

class AndroidLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            when {
                pluginManager.hasPlugin("com.android.application") ->
                    configure<ApplicationExtension> { lint(Lint::configure) }

                pluginManager.hasPlugin("com.android.library") ->
                    configure<LibraryExtension> { lint(Lint::configure) }

                else -> {
                    pluginManager.apply("com.android.lint")
                    configure<Lint>(Lint::configure)
                }

            }
            dependencies {
                "testImplementation"(libs.findLibrary("konsist").get())
            }
            with(pluginManager){
                apply(libs.findPlugin("ktlint").asString())
                //apply("org.jlleitschuh.gradle.ktlint")
                apply(libs.findPlugin("detekt").asString())
            }
            // Configure jvmTarget for gradle task `detekt`
            tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }

            // Configure jvmTarget for gradle task `detektGenerateBaseline`
            tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
            val extension = extensions.getByType<DetektExtension>()
            configureDetekt(extension)
            /*detekt{
                buildUponDefaultConfig = true
            }*/
        }
    }
}