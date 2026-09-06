import { defineCollection } from 'astro:content';
import { docsLoader } from '@astrojs/starlight/loaders';
import { docsSchema } from '@astrojs/starlight/schema';
import { craftingRecipeSchema, smithingRecipeSchema, recipeLoader } from './loaders/recipe-loader';

export const collections = {
	docs: defineCollection({ loader: docsLoader(), schema: docsSchema() }),
	craftingRecipes: defineCollection({
		loader: recipeLoader('crafting'),
		schema: craftingRecipeSchema
	}),
	smithingRecipes: defineCollection({
		loader: recipeLoader('smithing'),
		schema: smithingRecipeSchema
	}),
};
