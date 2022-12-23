package fr.pickaria

import fr.pickaria.menu.Command
import fr.pickaria.menu.Listeners
import fr.pickaria.shared.GlowEnchantment
import org.bukkit.plugin.java.JavaPlugin

/**
 * This function must be call in a JavaPlugin to init the library's features.
 */
fun JavaPlugin.enableBedrockLibrary() {
	getCommand("menu")?.setExecutor(Command()) ?: logger.severe("Cannot register command `menu`, did you forgot to add the command to plugin.yml?")
	server.pluginManager.registerEvents(Listeners(), this)
	GlowEnchantment.register()
}

var DEFAULT_MENU: String = "home"