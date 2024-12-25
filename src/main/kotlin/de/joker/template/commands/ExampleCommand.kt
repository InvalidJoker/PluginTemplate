package de.joker.template.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.tree.LiteralCommandNode
import de.joker.template.extensions.sendInfo
import de.joker.template.model.CommandBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands

class ExampleCommand : CommandBuilder {
    override fun register(): LiteralCommandNode<CommandSourceStack> {
        return Commands.literal("template")
            .executes { ctx ->
                ctx.source.sender.sendInfo("Hello World!")
                Command.SINGLE_SUCCESS
            }
            .build()
    }
}