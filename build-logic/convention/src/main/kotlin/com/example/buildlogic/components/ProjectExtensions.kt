/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.example.buildlogic.components

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.plugin.use.PluginDependency
import java.util.Optional

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Optional<Provider<PluginDependency>>.asString() = this.get().get().pluginId

internal fun Project.configureDetekt(extension: DetektExtension) = extension.apply {
    buildUponDefaultConfig = true // preconfigure defaults.
    allRules = false // activate all available (even unstable) rules.
    autoCorrect = false // To enable or disable auto formatting.
    parallel = true // To enable or disable parallel execution of detekt on multiple submodules.
    config.setFrom("${rootDir}/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior.
    baseline = file("${rootDir}/config/detekt/detekt-baseline.xml") // a way of suppressing issues before introducing detekt.
    tasks.named<Detekt>("detekt") {
        reports {
            xml.required.set(true)
            // observe findings in your browser with structure and code snippets
            html.required.set(true)
            // similar to the console output, contains issue signature to manually edit baseline files
            txt.required.set(true)
            sarif.required.set(true)
            // simple Markdown format
            md.required.set(true)
        }
    }
    dependencies {
        //"detektPlugins"(libs.findLibrary("detekt-formatting").get())
        "detektPlugins"(libs.findLibrary("detekt-compose").get())
    }
}

internal fun Project.isAnroidApplication() = pluginManager.hasPlugin(libs.findPlugin("android-application").asString())

internal fun Project.isAnroidLibrary() = pluginManager.hasPlugin(libs.findPlugin("android-library").asString())

internal fun Project.isAndroidModule() = isAnroidLibrary() || isAnroidApplication()