/**
 * Defines the structure of a model that can be viewed in the ModelViewer component.
 * Each model has a name, a path to the model file, and an optional animation name.
 */
export interface ViewerModel {
    name: string;
    path: string;
    animation?: string;
}

export interface Command {
    command: string;
    description: string;
    usage: string;
    permissionNode?: string;
    requiresOp?: boolean;
    controlledByConfig?: boolean;
}

export type Commands = Array<Command>;

export interface ModBadge {
    text: string;
    variant: "tip" | "note" | "danger" | "success" | "caution" | "default";
}

export interface Mod {
    name: string;
    docs: string;
    badge?: string | ModBadge;
}

export interface MolangFunction {
    function: string;
    result: string;
    description?: string;
}

export type MolangExtensions = Array<MolangFunction>;

export interface Ingredient {
    modId: string;
    itemId: string;
}

export interface CraftingRecipeVariant {
    name?: string;
    description?: string;
    ingredients: (string | Ingredient | null)[];
    resultItem: Ingredient | string;
    resultCount?: number;
}