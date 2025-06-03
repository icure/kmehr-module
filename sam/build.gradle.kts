@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed (https://youtrack.jetbrains.com/issue/KTIJ-19369)
plugins {
    id("com.icure.kotlin-library-conventions")

    alias(coreLibs.plugins.ksp)
    alias(coreLibs.plugins.mavenRepository)
    alias(coreLibs.plugins.gitVersion) apply(true)

    `maven-publish`
}

val gitVersion: String? by project

group = "org.taktik.icure"
version = gitVersion ?: "0.0.1-SNAPSHOT"

dependencies {

    val projectPrefix = when(rootProject.name) {
        "kmehr-importer" -> ":kmehr-module"
        else -> ""
    }

    implementation(project("$projectPrefix:kraken-common:core"))
    implementation(project("$projectPrefix:kraken-common:dao"))
    implementation(project("$projectPrefix:kraken-common:domain"))
    implementation(project("$projectPrefix:kraken-common:logic"))
    implementation(project("$projectPrefix:kraken-common:mapper"))
    implementation(project("$projectPrefix:kraken-common:dto"))
    implementation(project("$projectPrefix:kraken-common:utils"))

    implementation(coreLibs.bundles.swaggerLibs)
    implementation(coreLibs.bundles.krouchLibs)
    implementation(coreLibs.bundles.kotlinxCoroutinesLibs)
    implementation(coreLibs.caffeine)

    implementation(coreLibs.kotlinxCollectionsImmutableJvm)

    implementation(coreLibs.springBootWebflux)
    implementation(coreLibs.springBootSecurity)

    implementation(coreLibs.mapstruct)
    implementation(coreLibs.kmapKsp)

    ksp(group = "io.icure", name = "kmap", version = coreLibs.versions.kmap.orNull)
}
