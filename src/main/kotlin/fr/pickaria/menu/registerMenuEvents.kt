package fr.pickaria.menu

import org.bukkit.plugin.java.JavaPlugin

/**
 * This function must be call in a JavaPlugin to init the library's features.
 */
fun JavaPlugin.registerMenuEvents() {
	server.pluginManager.registerEvents(Listeners(), this)
}

var DEFAULT_MENU: String = "home"