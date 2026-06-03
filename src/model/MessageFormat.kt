package dev.invalidjoker.template.model

import de.joker.glue.extensions.send
import net.kyori.adventure.audience.Audience

enum class MessageFormat(private val format: String) {
    INFO("<color:#b2c2d4>%s"),
    WARNING("<color:#ffde66>%s"),
    ERROR("<color:#ff2e4a>%s"),
    SUCCESS("<color:#63ff9a>%s");

    fun send(sender: Audience, message: String, args: String? = null) {
        if (args == null) sender.send(Variables.PREFIX + format.format(message))
        else sender.send(Variables.PREFIX + format.format(args, message))
    }
}