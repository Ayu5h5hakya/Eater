val snapshotVersion : String? = System.getenv("COMPOSE_SNAPSHOT_ID")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/org/jetbrains/kotlin/kotlin-compose-compiler-plugin/2.0.0-RC2-200/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        snapshotVersion?.let {
            println("https://androidx.dev/snapshots/builds/$it/artifacts/repository/")
            maven { url = uri("https://androidx.dev/snapshots/builds/$it/artifacts/repository/") }
            maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/org/jetbrains/kotlin/kotlin-compose-compiler-plugin/2.0.0-RC2-200/") }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "Eater"
include(":app")
 