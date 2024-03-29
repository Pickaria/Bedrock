package fr.pickaria.menu

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

class Lore {
	var rightClick: String? = null
	var leftClick: String? = null
	private var description: MutableList<Component> = mutableListOf()
	private var keyValues: MutableList<Component> = mutableListOf()

	fun keyValues(init: KeyValuesBuilder.() -> Unit) = KeyValuesBuilder().init()

	inner class KeyValuesBuilder {
		infix fun String.to(value: Any) {
			if (value is Component) {
				val component = Component.text(this, NamedTextColor.GOLD)
					.append(Component.text(" : ", NamedTextColor.GOLD))
					.append(value.color(NamedTextColor.GRAY))
					.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
				keyValues.add(component)
			} else {
				val component = Component.text(this, NamedTextColor.GOLD)
					.append(Component.text(" : ", NamedTextColor.GOLD))
					.append(Component.text(value.toString(), NamedTextColor.GRAY))
					.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
				keyValues.add(component)
			}
		}
	}

	fun description(init: DescriptionBuilder.() -> Unit) = DescriptionBuilder().init()

	inner class DescriptionBuilder {
		operator fun String.unaryMinus() {
			val component = Component.text(this, NamedTextColor.GRAY)
				.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
			description.add(component)
		}

		operator fun Component.unaryMinus() {
			description.add(this)
		}
	}

	companion object {
		operator fun invoke(init: BuilderInit<Lore>) = Lore().apply(init)
	}

	fun build() = mutableListOf<Component>().apply {
		addAll(description)

		if (description.isNotEmpty() && keyValues.isNotEmpty()) {
			add(Component.empty())
		}

		addAll(keyValues)

		if ((!leftClick.isNullOrBlank() || !rightClick.isNullOrBlank()) && (keyValues.isNotEmpty() || description.isNotEmpty())) {
			add(Component.empty())
		}

		leftClick?.let { add(Component.text(it)) }
		rightClick?.let { add(Component.text(it)) }
	}
}