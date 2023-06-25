package fr.pickaria

import fr.pickaria.menu.Listeners
import fr.pickaria.shared.GlowEnchantment
import org.bukkit.plugin.java.JavaPlugin

/**
 * This function must be call in a JavaPlugin to init the library's features.
 */
fun JavaPlugin.enableBedrockLibrary() {
	server.pluginManager.registerEvents(Listeners(), this)
	GlowEnchantment.register()
}

var DEFAULT_MENU: String = "home"