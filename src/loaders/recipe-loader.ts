import { glob } from "astro/loaders";
import { z } from "astro/zod";

export const itemStringOrObject = z.union([
    z.string(),
    z.object({ modId: z.string(), itemId: z.string() })
]);

export const ingredientStringOrObject = z.union([
    z.string(),
    z.object({ modId: z.string(), itemId: z.string() }),
    z.null()
]);

export const baseRecipeSchema = z.object({
    name: z.string(),
    description: z.string().optional(),
    modFilter: z.string(),
    resultItem: itemStringOrObject,
    resultCount: z.number().optional().default(1),
});

export const craftingRecipeSchema = baseRecipeSchema.extend({
    ingredients: z.array(ingredientStringOrObject).length(9)
});

export const smithingRecipeSchema = baseRecipeSchema.extend({
    template: ingredientStringOrObject.optional(),
    baseItem: itemStringOrObject,
    additionItem: itemStringOrObject,
});

export type RecipeType = "crafting" | "smithing";

export const recipeLoader = (path: RecipeType) => glob({
    base: `./src/content/recipes/${path}`,
    pattern: '**/*.json'
});