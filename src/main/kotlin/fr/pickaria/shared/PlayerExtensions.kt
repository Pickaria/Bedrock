package fr.pickaria.shared

import fr.pickaria.shared.MiniMessage.Companion.miniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.advancement.Advancement
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


internal val chat = setupChat();

fun Player.updateDisplayName() {
	val displayName = prefix()
		.append(name().color(NamedTextColor.WHITE))
		.append(suffix())
	displayName(displayName)
	playerListName(displayName)
}

var Player.prefix: String
	get() = chat?.getPlayerPrefix(this) ?: ""
	set(value) = chat?.setPlayerPrefix(this, value) ?: Unit

var Player.suffix: String
	get() = chat?.getPlayerSuffix(this) ?: ""
	set(value) = chat?.setPlayerSuffix(this, value) ?: Unit

fun Player.prefix(): Component = prefix.let {
	if (it.isNotEmpty()) {
		miniMessage.deserialize(it).append(Component.text(" "))
	} else {
		Component.empty()
	}
}

fun Player.suffix(): Component = suffix.let {
	if (it.isNotEmpty()) {
		Component.text(" ").append(miniMessage.deserialize(it))
	} else {
		Component.empty()
	}
}

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