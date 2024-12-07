package de.joker.template

import de.joker.template.listeners.ItemClickListener
import org.bukkit.plugin.java.JavaPlugin

class PluginInstance : JavaPlugin() {

    override fun onLoad() {
        server.pluginManager.registerEvents(ItemClickListener(), this)

        logger.info("Template plugin loaded.")
    }

    override fun onEnable() {
        logger.info("Template plugin enabled.")
    }
}