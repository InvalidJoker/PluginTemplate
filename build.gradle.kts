plugins {
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"
    alias(libs.plugins.userdev)
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.shadow)
}

group = "dev.invalidjoker"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "invalidjokerSnapshots"
        url = uri("https://maven.invalidjoker.dev/snapshots")
    }
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

paperweight {
    reobfArtifactConfiguration = io.papermc.paperweight.userdev
        .ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper.get() + "-R0.1-SNAPSHOT")

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.glue.paper)
    implementation(libs.glue.core)

    implementation(libs.commandapi.bukkit.shade)
    implementation(libs.commandapi.bukkit.kotlin)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion(libs.versions.paper.get())
    }

    shadowJar {
        dependsOn("processResources")
        archiveBaseName.set(project.name)
    }
}

kotlin {
    jvmToolchain(25)

    sourceSets {
        main {
            kotlin.srcDirs("src")
        }
        test {
            kotlin.srcDirs("test")
        }
    }
}

paper {
    main = "dev.invalidjoker.template.PluginTemplate"
    apiVersion = "1.21"
    name = "PluginTemplate"
    version = project.version.toString()
    authors = listOf("InvalidJoker")
}
