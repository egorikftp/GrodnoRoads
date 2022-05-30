apply(plugin = "com.github.ben-manes.versions")

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.gradle.plugin.buildtools)
        classpath(libs.gradle.plugin.crashlytics)
        classpath(libs.gradle.plugin.googleservices)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.ksp)
        classpath(libs.gradle.plugin.versioncheck)
        classpath(libs.gradle.plugin.secrets)
        classpath(libs.gradle.plugin.serialization)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}

//assembleRelease -Pgrodnoroads.enableComposeCompilerReports=true
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("grodnoroads.enableComposeCompilerReports") == "true") {
                freeCompilerArgs = freeCompilerArgs +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_metrics" +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_metrics"
            }
        }
    }
}