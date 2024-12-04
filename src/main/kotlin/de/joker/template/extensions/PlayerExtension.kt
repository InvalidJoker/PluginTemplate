package de.joker.template.extensions

import de.joker.template.model.MessageFormat
import org.bukkit.command.CommandSender


fun CommandSender.sendMessageFormated(message: String, format: MessageFormat, args: String? = null) = format.sendCommandSender(this, message, args)

fun CommandSender.sendInfo(message: String) = sendMessageFormated(message, MessageFormat.INFO)
fun CommandSender.sendWarning(message: String) = sendMessageFormated(message, MessageFormat.WARNING)
fun CommandSender.sendError(message: String) = sendMessageFormated(message, MessageFormat.ERROR)
fun CommandSender.sendSuccess(message: String) = sendMessageFormated(message, MessageFormat.SUCCESS)