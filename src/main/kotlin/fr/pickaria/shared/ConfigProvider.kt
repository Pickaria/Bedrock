package fr.pickaria.shared

import io.papermc.paper.inventory.ItemRarity
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.Villager
import org.bukkit.loot.LootTable
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

fun String.toSnakeCase(): String = camelRegex.replace(this) {
	"_${it.value}"
}.lowercase()

open class ConfigProvider(var section: ConfigurationSection? = null) {
	protected val miniMessageDeserializer: MiniMessageDeserializer by lazy {
		MiniMessageDeserializer()
	}

	protected inline fun <reified T : ConfigProvider> sectionLoader(): SectionLoader<T> = SectionLoader(T::class)

	protected inner class SectionLoader<T: ConfigProvider>(private val type: KClass<T>) {
		operator fun getValue(config: ConfigProvider, property: KProperty<*>): Map<String, T> =
			section?.getConfigurationSection(property.name.toSnakeCase())?.let {
				it.getKeys(false).associateWith { key ->
					type.constructors.first().call().apply {
						it.getConfigurationSection(key)?.let { config -> setConfig(config) }
					}
				}
			} ?: mapOf()
	}

	protected inline operator fun <reified T> getValue(thisRef: ConfigProvider, property: KProperty<*>): T =
		with(property.name.toSnakeCase()) {
			when (T::class) {
				Component::class -> {
					section?.getString(this)?.let { Component.text(it) } as? T
				}

				LootTable::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						val (namespace, key) = it.split(':')
						Bukkit.getLootTable(NamespacedKey(namespace, key))
					} as? T
				}

				Material::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						Material.getMaterial(it)
					} as? T
				}

				Sound::class -> {
					section?.getString(this)?.let { Sound.sound(Key.key(it), Sound.Source.MASTER, 1f, 1f) } as? T
				}

				ItemRarity::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						ItemRarity.valueOf(it)
					} as? T
				}

				EnchantmentTarget::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						EnchantmentTarget.valueOf(it)
					} as? T
				}

				Villager.Profession::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						Villager.Profession.valueOf(it)
					} as? T
				}

				Villager.Type::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						Villager.Type.valueOf(it)
					} as? T
				}

				UUID::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						UUID.fromString(it)
					} as? T
				}

				World::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						Bukkit.getWorld(it)
					} as? T
				}

				World::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						Bukkit.getWorld(it)
					} as? T
				}

				BossBar.Color::class -> {
					section?.getString(property.name.toSnakeCase())?.let {
						BossBar.Color.valueOf(it)
					} as? T
				}

				else -> {
					section?.get(this) as? T
				}
			} ?: throw Exception("Property `$this` not found in config file.")
		}

	fun setConfig(config: ConfigurationSection) {
		this.section = config
	}

	protected inner class MiniMessageDeserializer {
		private val miniMessage = MiniMessage.miniMessage

		operator fun getValue(config: ConfigProvider, property: KProperty<*>): Component =
			section?.getString(property.name.toSnakeCase())?.let {
				miniMessage.deserialize(it)
			} ?: Component.text("")
	}
}