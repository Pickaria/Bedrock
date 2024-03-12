package fr.pickaria.shared

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player


internal val chat = setupChat()

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
        MiniMessage.miniMessage.deserialize(it).append(Component.text(" "))
    } else {
        Component.empty()
    }
}

fun Player.suffix(): Component = suffix.let {
    if (it.isNotEmpty()) {
        Component.text(" ").append(MiniMessage.miniMessage.deserialize(it))
    } else {
        Component.empty()
    }
}