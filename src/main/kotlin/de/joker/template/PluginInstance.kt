package de.joker.template

import de.joker.template.extensions.register
import de.joker.template.listeners.ItemClickListener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class PluginInstance : JavaPlugin() {

    companion object {
        lateinit var instance: PluginInstance
    }
    override fun onEnable() {

        instance = this
        // Plugin startup logic
        val time = measureTimeMillis {
            ItemClickListener().register()
        }
        logger.info("SkyRealmLobby enabled in $time ms")
    }
}