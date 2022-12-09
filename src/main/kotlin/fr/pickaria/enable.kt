package fr.pickaria

import fr.pickaria.menu.Command
import fr.pickaria.menu.Listeners
import fr.pickaria.shared.GlowEnchantment
import fr.pickaria.shared.setupChat
import org.bukkit.plugin.java.JavaPlugin

/**
 * This function must be call in a JavaPlugin to init the library's features.
 */
fun JavaPlugin.enableBedrockLibrary() {
	getCommand("menu")?.setExecutor(Command())
	server.pluginManager.registerEvents(Listeners(), this)
	GlowEnchantment.register(this)
}

var DEFAULT_MENU: String = "home"