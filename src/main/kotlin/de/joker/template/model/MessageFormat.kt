package de.joker.template.model

import de.joker.template.extensions.text
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

enum class MessageFormat(val format: String) {

    INFO("<color:#CAD3C8>INFO: %s"),
    WARNING("<color:#F8EFBA>WARNING: %s"),
    ERROR("<color:#FD7272>ERROR: %s"),
    SUCCESS("<color:#55E6C1>SUCCESS: %s");

    fun sendPlayer(player: Player, message: String, args: String? = null) {
        if (args == null) player.sendMessage(text(format.format(message)))
        else player.sendMessage(text(format.format(args, message)))
    }

    fun sendCommandSender(sender: CommandSender, message: String, args: String? = null) {
        if (args == null) sender.sendMessage(text(format.format(message)))
        else sender.sendMessage(text(format.format(args, message)))
    }
}