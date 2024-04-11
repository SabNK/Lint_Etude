// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //`kotlin-dsl` apply false
    //kotlin("jvm") apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.dsl) apply false


    //alias(libs.plugins.android.lint) apply false
}
println("hello============================")
println("$rootDir")

//detekt {
//    source.setFrom("src/main/java", "src/main/kotlin")
//    debug = false
//    parallel = true
//    config.setFrom("${rootProject.projectDir}/default-detekt-config.yml")
//    baseline = file("${rootProject.projectDir}/detekt-baseline.xml")
//    buildUponDefaultConfig = true
//    allRules = false
//}



