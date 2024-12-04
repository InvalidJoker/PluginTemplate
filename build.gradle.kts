plugins {
    kotlin("jvm") version "2.0.21"
    alias(libs.plugins.paper.yml)
    alias(libs.plugins.run.server)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadow)
}

group = "de.joker"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraftVersion.get() + "-R0.1-SNAPSHOT")
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
        mergeServiceFiles()
        configurations = listOf(project.configurations.shadow.get())
        archiveFileName.set("PluginTemplate.jar")
    }
}
kotlin {
    jvmToolchain(21)
}

paper {
    main = "de.joker.template.PluginInstance"
    loader = "de.joker.template.DependencyLoader"
    apiVersion = "1.19"
    name = "PluginTemplate"
    authors = listOf("InvalidJoker")
    generateLibrariesJson = true
}
