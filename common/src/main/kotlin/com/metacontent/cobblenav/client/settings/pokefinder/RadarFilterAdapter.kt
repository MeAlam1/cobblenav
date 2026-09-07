package com.metacontent.cobblenav.client.settings.pokefinder

import com.google.gson.*
import com.metacontent.cobblenav.client.settings.pokefinder.filter.RadarFilter
import com.metacontent.cobblenav.utils.extensions.asIdentifier
import java.lang.reflect.Type

object RadarFilterAdapter : JsonSerializer<RadarFilter>, JsonDeserializer<RadarFilter> {

	override fun serialize(src: RadarFilter, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
		val jsonObject = context
			.serialize(src, src.javaClass)
			.asJsonObject

		jsonObject.remove("type")
		jsonObject.addProperty("type", src.type.toString())

		return jsonObject
	}

	override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RadarFilter {
		val jsonObject = json.asJsonObject

		val typeElement = jsonObject["type"]
			?: throw JsonParseException("Missing 'type' in RadarFilter")

		val typeName = when {
			typeElement.isJsonPrimitive -> {
				typeElement.asString
			}

			typeElement.isJsonObject -> {
				val typeObject = typeElement.asJsonObject

				val namespace = typeObject["namespace"]?.asString
					?: throw JsonParseException(
						"Missing 'namespace' in RadarFilter type",
					)

				val path = typeObject["path"]?.asString
					?: throw JsonParseException(
						"Missing 'path' in RadarFilter type",
					)

				"$namespace:$path"
			}

			else -> {
				throw JsonParseException(
					"Invalid RadarFilter type: $typeElement",
				)
			}
		}

		val typeId = typeName.asIdentifier()

		val filterType = RadarFilterTypeRegistry.get(typeId)
			?: throw JsonParseException(
				"Unknown filter type: $typeId",
			)

		jsonObject.remove("type")

		return context.deserialize(
			jsonObject,
			filterType.filterClass,
		)
	}
}
