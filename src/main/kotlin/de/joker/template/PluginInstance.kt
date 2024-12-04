package de.joker.template

import org.bukkit.plugin.java.JavaPlugin

class PluginInstance : JavaPlugin() {

    override fun onLoad() {
        logger.info("Template plugin loaded.")
    }

    override fun onEnable() {
        logger.info("Template plugin enabled.")
    }
}