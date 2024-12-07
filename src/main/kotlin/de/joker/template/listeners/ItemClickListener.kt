package de.joker.template.listeners

import de.joker.template.model.gui.InventoryItem
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemClickListener : Listener {
    companion object {
        val itemClickEvents: MutableMap<ItemStack, (event: InventoryClickEvent) -> Unit> = mutableMapOf()
        private val inventoryMap = mutableMapOf<UUID, RegisteredInventory>()

        fun registerInventory(player: Player, inventory: Inventory, items: Map<Int, InventoryItem>) {
            inventoryMap[player.uniqueId] = RegisteredInventory(inventory, items)
        }
    }




    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val registeredInventory = inventoryMap[player.uniqueId] ?: return

        if (event.clickedInventory == registeredInventory.inventory) {
            event.isCancelled = true
            val clickedItem = registeredInventory.items[event.slot]
            clickedItem?.onClick?.invoke(event)
        }
    }

    data class RegisteredInventory(
        val inventory: Inventory,
        val items: Map<Int, InventoryItem>
    )

    @EventHandler
    fun onItemInventoryClick(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val action = itemClickEvents[item] ?: return
        action(event)
    }
}