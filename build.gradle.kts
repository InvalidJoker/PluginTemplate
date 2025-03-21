plugins {
    kotlin("jvm") version "2.1.0"
    alias(libs.plugins.paper.yml)
    alias(libs.plugins.run.server)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadow)
    kotlin("plugin.serialization") version "2.1.20"
}

group = "de.joker"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraftVersion.get() + "-R0.1-SNAPSHOT")

    paperLibrary(libs.serialization)
    paperLibrary(kotlin("reflect"))
}

tasks {

    build {
        dependsOn(reobfJar)
    }

    runServer {
        minecraftVersion(libs.versions.minecraftVersion.get())
    }

    reobfJar {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set("PluginTemplate.jar")
    }
}
kotlin {
    jvmToolchain(21)
}

paper {
    main = "de.joker.template.PluginInstance"
    loader = "de.joker.template.DependencyLoader"
    bootstrapper = "de.joker.template.CommandBootstrapper"
    apiVersion = "1.19"
    name = "PluginTemplate"
    authors = listOf("InvalidJoker")
    generateLibrariesJson = true
}
