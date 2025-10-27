package de.joker.template.commands

import de.joker.template.extensions.sendError
import de.joker.template.extensions.sendSuccess
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor

class ExampleCommand {
    private val command = commandTree("example") {
        literalArgument("success") {
            playerExecutor { player, _ ->
                player.sendSuccess("Works as expected!")
            }
        }

        literalArgument("error") {
            playerExecutor { player, _ ->
                player.sendError("Something went wrong!")
            }
        }
    }
}