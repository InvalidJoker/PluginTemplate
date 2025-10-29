package dev.invalidjoker.template

import de.joker.kutils.paper.KPlugin
import dev.invalidjoker.template.commands.ExampleCommand
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIPaperConfig

class PluginTemplate : KPlugin() {

    companion object {
        lateinit var instance: PluginTemplate
    }

    override fun load() {
        CommandAPI.onLoad(CommandAPIPaperConfig(this).setNamespace("minecraft"))
    }

    override fun startup() {
        instance = this
        CommandAPI.onEnable()

        ExampleCommand()

        logger.info("PluginTemplate enabled")
    }

    override fun shutdown() {
        CommandAPI.onDisable()
        logger.info("PluginTemplate disabled")
    }
}