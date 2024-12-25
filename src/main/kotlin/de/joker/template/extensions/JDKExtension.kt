package de.joker.template.extensions

import de.joker.template.PluginInstance
import org.bukkit.event.Listener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun getLogger(): Logger {
    return LoggerFactory.getLogger(PluginInstance::class.java)
}

fun <T : Any> T.nullIf(condition: (T) -> Boolean): T? {
    return if (condition(this)) null else this
}

fun Listener.register() {
    PluginInstance.instance.server.pluginManager.registerEvents(this, PluginInstance.instance)
}