package de.joker.template

import de.joker.template.commands.ExampleCommand
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

class CommandBootstrapper : PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        val manager = context.lifecycleManager

        manager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            ExampleCommand().register(event.registrar())
        }
    }
}