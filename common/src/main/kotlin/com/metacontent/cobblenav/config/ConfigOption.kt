package com.metacontent.cobblenav.config

sealed class ConfigOption<V : Any>(
	val name: String,
	private val getter: () -> V,
	private val setter: (V) -> Unit,
	private val baseValue: V,
) {
	fun get(): V = getter()

	fun set(value: V) = setter(value)

	fun reset() = setter(baseValue)

	fun isAtBaseValue(): Boolean = get() == baseValue

	@Suppress("UNCHECKED_CAST")
	fun setUnchecked(value: Any) {
		setter(value as V)
	}

	class BooleanOption(
		name: String,
		getter: () -> Boolean,
		setter: (Boolean) -> Unit,
		baseValue: Boolean,
	) : ConfigOption<Boolean>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	)

	class IntOption(
		name: String,
		getter: () -> Int,
		setter: (Int) -> Unit,
		val range: IntRange? = null,
		baseValue: Int,
	) : ConfigOption<Int>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	) {
		constructor(name: String, getter: () -> Int, setter: (Int) -> Unit, baseValue: Int) : this(
			name,
			getter,
			setter,
			null,
			baseValue,
		)
	}

	class LongOption(
		name: String,
		getter: () -> Long,
		setter: (Long) -> Unit,
		baseValue: Long,
	) : ConfigOption<Long>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	)

	class FloatOption(
		name: String,
		getter: () -> Float,
		setter: (Float) -> Unit,
		val range: ClosedFloatingPointRange<Float>? = null,
		baseValue: Float,
	) : ConfigOption<Float>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	) {
		constructor(name: String, getter: () -> Float, setter: (Float) -> Unit, baseValue: Float) : this(
			name,
			getter,
			setter,
			null,
			baseValue,
		)
	}

	class DoubleOption(
		name: String,
		getter: () -> Double,
		setter: (Double) -> Unit,
		val range: ClosedFloatingPointRange<Double>? = null,
		baseValue: Double,
	) : ConfigOption<Double>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	) {
		constructor(name: String, getter: () -> Double, setter: (Double) -> Unit, baseValue: Double) : this(
			name,
			getter,
			setter,
			null,
			baseValue,
		)
	}

	class StringOption(
		name: String,
		getter: () -> String,
		setter: (String) -> Unit,
		baseValue: String,
	) : ConfigOption<String>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	)

	class EnumOption<E : Enum<E>>(
		name: String,
		getter: () -> E,
		setter: (E) -> Unit,
		val values: List<E>,
		baseValue: E,
	) : ConfigOption<E>(
		name = name,
		getter = getter,
		setter = setter,
		baseValue = baseValue,
	)
}
