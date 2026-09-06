import { globSync, readFileSync } from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";
import type { Mod } from "../types";

function removeLeadingSlash(str: string): string {
  return str.startsWith("/") ? str.slice(1) : str;
}

function removeTrailingSlash(str: string): string {
  return str.endsWith("/") ? str.slice(0, -1) : str;
}

function makeDocsLink(str: string): string {
  str = str.replace(/\\/g, "/").replace(/\.mdx$/, "").replace(/index$/, "");
  // Remove trailing slash if present
  str = removeTrailingSlash(str);
  // Remove leading slash if present
  return removeLeadingSlash(str);
}

/**
 * Reads a file's YAML frontmatter and returns the value of `sidebar.order`,
 * or `Infinity` if the field is absent or unreadable.
 */
function getSidebarOrder(filePath: string): number {
  try {
    const content = readFileSync(filePath, "utf-8");
    const fmMatch = content.match(/^---\r?\n([\s\S]*?)\r?\n---/);
    if (!fmMatch) return Infinity;

    const fm = fmMatch[1];
    // Capture the indented block that belongs to the top-level "sidebar:" key
    const sidebarMatch = fm.match(/^sidebar:\s*\n((?:[ \t]+[^\n]*\n?)*)/m);
    if (!sidebarMatch) return Infinity;

    const sidebarBlock = sidebarMatch[1];
    const orderMatch = sidebarBlock.match(/^\s+order:\s*(\d+)/m);
    if (!orderMatch) return Infinity;

    return parseInt(orderMatch[1], 10);
  } catch {
    return Infinity;
  }
}

function prettyFolderName(str: string): string {
  return str.replace(/-/g, " ").replace(/\b\w/g, (char) => char.toUpperCase());
}

export function buildSidebarFromGlob(mod: Mod) {
  const dir = path.dirname(fileURLToPath(import.meta.url));
  const contentDocsBase = path.join(dir, "./content/docs/");
  const modBase = path.join(contentDocsBase, mod.docs);
  const pattern = `${modBase}/**/*.mdx`;
  const files = globSync(pattern);

  type OrderedLink = { link: string; order: number };

  const rootItems: OrderedLink[] = [];
  const folderMap = new Map<string, OrderedLink[]>();

  for (const file of files) {
    const relativePath = path.relative(contentDocsBase, file);
    const link = makeDocsLink(relativePath);
    const relToMod = path.relative(modBase, file);
    const parts = relToMod.split(path.sep);
    const order = getSidebarOrder(file);

    if (parts.length === 1) {
      rootItems.push({ link, order });
    } else {
      const folder = parts[0];
      const existing = folderMap.get(folder);
      if (existing) {
        existing.push({ link, order });
      } else {
        folderMap.set(folder, [{ link, order }]);
      }
    }
  }

  const sortByOrder = (a: OrderedLink, b: OrderedLink) => a.order - b.order;

  const folderGroups = Array.from(folderMap.entries()).map(
    ([folder, folderItems]) => ({
      label: prettyFolderName(folder),
      collapsed: true as const,
      items: folderItems.sort(sortByOrder).map((item) => item.link),
    })
  );

  return {
    label: mod.name,
    collapsed: true,
    ...(mod.badge
      ? {
        badge:
          typeof mod.badge === "string"
            ? { text: mod.badge, variant: "tip" as const }
            : mod.badge,
      }
      : {}),
    items: [...rootItems.sort(sortByOrder).map((item) => item.link), ...folderGroups],
  };
}
