plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    alias(libs.plugins.userdev)
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.run.paper)
}

group = "dev.invalidjoker"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://nexus.fruxz.dev/repository/public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

paperweight {
    reobfArtifactConfiguration = io.papermc.paperweight.userdev
        .ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper.get() + "-R0.1-SNAPSHOT")

    paperLibrary(libs.kotlinx.serialization.json)
    paperLibrary(kotlin("stdlib"))

    paperLibrary(libs.kutils.paper)
    paperLibrary(libs.kutils.adventure)

    paperLibrary(libs.commandapi.bukkit.shade)
    paperLibrary(libs.commandapi.bukkit.kotlin)
}

tasks {
    build {
        dependsOn(reobfJar)
    }

    runServer {
        minecraftVersion(libs.versions.paper.get())
    }

    jar {
        archiveFileName.set(project.name + ".jar")
    }
}
kotlin {
    jvmToolchain(21)
}

paper {
    main = "dev.invalidjoker.template.PluginTemplate"
    loader = "dev.invalidjoker.template.DependencyLoader"
    apiVersion = "1.21"
    name = "PluginTemplate"
    authors = listOf("InvalidJoker")
    generateLibrariesJson = true
}
