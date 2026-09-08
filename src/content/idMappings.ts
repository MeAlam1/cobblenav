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