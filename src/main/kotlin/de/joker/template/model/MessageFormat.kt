package de.joker.template.model

import de.joker.kutils.adventure.send
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

enum class MessageFormat(private val format: String) {

    INFO("<color:#b2c2d4>%s"),
    WARNING("<color:#ffde66>%s"),
    ERROR("<color:#ff2e4a>%s"),
    SUCCESS("<color:#63ff9a>%s");

    fun sendPlayer(player: Player, message: String, args: String? = null) {
        if (args == null) player.sendMessage(Variables.PREFIX + format.format(message))
        else player.sendMessage(Variables.PREFIX + format.format(args, message))
    }

    fun sendCommandSender(sender: CommandSender, message: String, args: String? = null) {
        if (args == null) sender.send(Variables.PREFIX + format.format(message))
        else sender.send(Variables.PREFIX + format.format(args, message))
    }

}