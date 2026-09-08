import type { Ingredient } from "../types";

/**
 * This interface represents a hyperlink object with an href property.
 * The href property is a string that contains the URL of the hyperlink.
 */
export interface Hyperlink {
    href: string;
}

/**
 * This type represents a mapping of IDs in the format "modId:itemId" to a hyperlink object
 */
export type IdMappings = Record<`${string}:${string}`, Hyperlink>;

/**
 * Generates a hyperlink to the Cobblemon Wiki for a given item.
 * 
 * @param item - The name of the item to generate a hyperlink for.
 * @returns A string representing the URL to the Cobblemon Wiki page for the specified item.
 */
const cobblemonWiki = (item: string): string => `https://wiki.cobblemon.com/index.php/${item}`;

/**
 * Generates a hyperlink to the Minecraft Wiki for a given item.
 * 
 * @param item - The name of the item to generate a hyperlink for.
 * @returns A string representing the URL to the Minecraft Wiki page for the specified item.
 */
const minecraftWiki = (item: string): string => `https://minecraft.wiki/w/${item}`;

/**
 * This object contains mappings of specific item IDs to their corresponding hyperlinks on the Cobblemon Wiki.
 * Each key is in the format "modId:itemId" and maps to a Hyperlink object containing the URL.
 */
export const idMappings: IdMappings = {
    "cobblemon:electirizer": { href: cobblemonWiki("Electirizer") },
    "cobblemon:upgrade": { href: cobblemonWiki("Upgrade") },

    "cobblemon:ghost_gem": { href: cobblemonWiki("Type_Gems") },
    "cobblemon:water_gem": { href: cobblemonWiki("Type_Gems") },

    "cobblemon:relic_coin": { href: cobblemonWiki("Relic_Coin") },

    "cobblemon:black_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:blue_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:green_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:pink_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:red_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:white_apricorn": { href: cobblemonWiki("Apricorn") },
    "cobblemon:yellow_apricorn": { href: cobblemonWiki("Apricorn") },

    "minecraft:compass": { href: minecraftWiki("Compass") },
    "minecraft:copper_ingot": { href: minecraftWiki("Copper_ingot") },
    "minecraft:diamond": { href: minecraftWiki("Diamond") },
    "minecraft:iron_ingot": { href: minecraftWiki("Iron_ingot") },
    "minecraft:prismarine_crystals": { href: minecraftWiki("Prismarine_crystals") },
    "minecraft:prismarine_shard": { href: minecraftWiki("Prismarine_shard") },
    "minecraft:redstone": { href: minecraftWiki("Redstone") },

    "minecraft:black_dye": { href: minecraftWiki("Dye") },
    "minecraft:blue_dye": { href: minecraftWiki("Dye") },
    "minecraft:brown_dye": { href: minecraftWiki("Dye") },
    "minecraft:cyan_dye": { href: minecraftWiki("Dye") },
    "minecraft:gray_dye": { href: minecraftWiki("Dye") },
    "minecraft:green_dye": { href: minecraftWiki("Dye") },
    "minecraft:light_blue_dye": { href: minecraftWiki("Dye") },
    "minecraft:light_gray_dye": { href: minecraftWiki("Dye") },
    "minecraft:lime_dye": { href: minecraftWiki("Dye") },
    "minecraft:magenta_dye": { href: minecraftWiki("Dye") },
    "minecraft:orange_dye": { href: minecraftWiki("Dye") },
    "minecraft:pink_dye": { href: minecraftWiki("Dye") },
    "minecraft:purple_dye": { href: minecraftWiki("Dye") },
    "minecraft:red_dye": { href: minecraftWiki("Dye") },
    "minecraft:white_dye": { href: minecraftWiki("Dye") },
    "minecraft:yellow_dye": { href: minecraftWiki("Dye") },
};

/**
 * Retrieves the mapped hyperlink URL for a given item or ingredient if one exists.
 *
 * @param item - The item identifier (e.g. "copper_ingot", "minecraft:compass", or an Ingredient object) or null
 * @returns The hyperlink URL or null if no mapping is found.
 */
export const getItemHyperlink = (
    item: string | Ingredient | null | undefined
): string | null => {
    if (!item) return null;
    let key: `${string}:${string}`;
    if (typeof item === "string") {
        key = item.includes(":") ? (item as `${string}:${string}`) : `minecraft:${item}`;
    } else {
        key = `${item.modId}:${item.itemId}`;
    }
    return idMappings[key]?.href ?? null;
};