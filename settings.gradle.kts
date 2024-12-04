pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "PluginTemplate"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("serialization", "org.jetbrains.kotlinx", "kotlinx-serialization-json").version("1.7.1")

            plugin("paper.yml", "net.minecrell.plugin-yml.paper").version("0.6.0")
            plugin("run.server","xyz.jpenilla.run-paper").version("2.3.1")
            plugin("paperweight", "io.papermc.paperweight.userdev").version("1.7.6")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")



            version("minecraftVersion", "1.21.3")
        }
    }
}

