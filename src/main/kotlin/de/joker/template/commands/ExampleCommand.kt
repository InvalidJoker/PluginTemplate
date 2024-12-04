package de.joker.template.commands

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import de.joker.template.extensions.sendError
import de.joker.template.extensions.sendInfo
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.entity.Player

class ExampleCommand : Command<CommandSourceStack> {

    override fun run(context: CommandContext<CommandSourceStack>): Int {
        val sender = context.source.sender
        if (sender !is Player) {
            sender.sendError("You must be a player to use this command.")
            return Command.SINGLE_SUCCESS
        }

        sender.sendInfo("This is an example command.")

        return Command.SINGLE_SUCCESS
    }

    companion object {
        fun register(): LiteralCommandNode<CommandSourceStack> {
            return Commands.literal("example")
                .executes(ExampleCommand())
                .build()
        }
    }
}