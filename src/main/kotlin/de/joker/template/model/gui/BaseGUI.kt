package de.joker.template.model.gui

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class BaseGUI<T> {

    protected abstract val size: Int
    protected abstract val title: Component

    // Now accepts arguments of any type T
    protected abstract fun buildInventory(player: Player, context: T): InventoryBuilder

    // When opening, pass the context argument
    fun open(player: Player, context: T) {
        val builder = buildInventory(player, context)
        builder.open(player)
    }
}