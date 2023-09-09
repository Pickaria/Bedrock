package fr.pickaria.menu

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

internal typealias ClickHandler = ((InventoryClickEvent) -> Unit)
typealias BuilderInit<T> = T.() -> Unit

internal val builders: MutableMap<String, BuilderInit<Menu.Builder>> = mutableMapOf()

data class Entry(val key: String, val init: BuilderInit<Menu.Builder>)

fun register(name: String, builder: BuilderInit<Menu.Builder>): Entry {
	if (builders.containsKey(name)) {
		Bukkit.getServer().logger.warning("Registering menu `$name` but is already registered.")
	}

	builders[name] = builder
	Bukkit.getServer().logger.fine("Menu `$name` registered.")

	return Entry(name, builder)
}

fun unregister(name: String) {
	if (!builders.containsKey(name)) {
		Bukkit.getServer().logger.warning("Trying to unregister menu `$name` but is not registered.")
	} else {
		builders.remove(name)
	}
}

fun menu(init: BuilderInit<Menu.Builder>) = init

fun menu(name: String, init: BuilderInit<Menu.Builder>) = register(name, init)

/**
 * Opens an already instantiated menu.
 */
fun Player.open(menu: Menu) {
	openInventory(menu.inventory())
}

/**
 * Builds and opens a menu at a given page.
 */
fun Player.open(init: BuilderInit<Menu.Builder>) {
	val previous = (openInventory.topInventory.holder as? Holder)?.menu
	open(Menu(init, this@open, previous, 0).build())
}

/**
 * Builds and opens a menu at a given page.
 */
fun Player.open(init: BuilderInit<Menu.Builder>, previous: Menu?) {
	open(Menu(init, this@open, previous, 0).build())
}

/**
 * Builds and opens a menu at a given page.
 */
fun Player.open(menu: String): Boolean =
	builders[menu]?.let {
		val previous = (openInventory.topInventory.holder as? Holder)?.menu
		open(Menu(it, this@open, previous, 0, menu).build())
		true
	} ?: false

/**
 * Builds and opens a menu at a given page, the previous for the new menu is the previous menu's previous.
 */
fun Player.open(menu: String, page: Int): Boolean =
	builders[menu]?.let {
		val previous = (openInventory.topInventory.holder as? Holder)?.menu?.previous
		open(Menu(it, this@open, previous, page, menu).build())
		true
	} ?: false

fun Player.isInMenu(): Boolean = openInventory.topInventory.holder as? Holder != null

fun Player.getCurrentMenu(): Menu? = (openInventory.topInventory.holder as? Holder)?.menu
