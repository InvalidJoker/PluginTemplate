package dev.invalidjoker.template.extensions

import dev.invalidjoker.template.model.MessageFormat
import net.kyori.adventure.audience.Audience

fun Audience.sendMessageFormated(message: String, format: MessageFormat, args: String? = null) = format.send(this, message, args)

fun Audience.sendInfo(message: String) = sendMessageFormated(message, MessageFormat.INFO)
fun Audience.sendWarning(message: String) = sendMessageFormated(message, MessageFormat.WARNING)
fun Audience.sendError(message: String) = sendMessageFormated(message, MessageFormat.ERROR)
fun Audience.sendSuccess(message: String) = sendMessageFormated(message, MessageFormat.SUCCESS)