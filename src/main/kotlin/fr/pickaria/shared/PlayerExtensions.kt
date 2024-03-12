package fr.pickaria.shared

import org.bukkit.Material
import org.bukkit.advancement.Advancement
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Player.grantAdvancement(advancement: Advancement) {
	val progress = getAdvancementProgress(advancement)
	progress.remainingCriteria.forEach {
		progress.awardCriteria(it)
	}
}

/**
 * Gives items into a HumanEntity's inventory and drop what doesn't fit on the ground.
 */
fun HumanEntity.give(vararg items: ItemStack) {
	inventory.addItem(*items).forEach {
		val location = eyeLocation
		val item = location.world.dropItem(location, it.value)
		item.velocity = location.direction.multiply(0.3)
	}
}

/**
 * Sums the amount of all stacks corresponding to given material.
 */
fun Inventory.sum(material: Material) = contents.filterNotNull().filter { it.type == material }.sumOf { it.amount }
