package de.joker.template.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer


val adventureSerializer = LegacyComponentSerializer
    .builder().extractUrls().hexColors().build()

val mm = MiniMessage.miniMessage()

val plainAdventureSerializer = PlainTextComponentSerializer.plainText()

val ComponentLike.asString: String
    get() = adventureSerializer.serialize(asComponent())

val ComponentLike.asPlainString: String
    get() = plainAdventureSerializer.serialize(asComponent())

val ComponentLike.content
    get() = asPlainString

fun text(text: ComponentLike): Component {
    return mm.deserialize(text.asString)
}

fun text(text: String): Component {
    return mm.deserialize(text)
}