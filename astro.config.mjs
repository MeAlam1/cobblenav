// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightSocialIcons from './src/plugins/socialIcons';

// https://astro.build/config
export default defineConfig({
    site: "https://changeme.com",

	integrations: [
		starlightSocialIcons({
			// maven: "",
			modrinth: "https://modrinth.com/mod/cobblemon-pokenav",
			curseforge: "https://www.curseforge.com/minecraft/mc-mods/cobblemon-pokenav",
		}),

		starlight({
			title: 'Cobblenav Docs',
			description: 'Cobblenav is a sidemod that adds a pokenav item similar to the one featured in the Pokemon RS games, that allows you to check spawns without the command and more in the future',

			social: [
				{ icon: 'github', label: 'GitHub', href: 'https://github.com/MeAlam1/cobblenav' },
				// { icon: 'discord', label: 'Discord', href: '' },
			],

			components: {
				ContentPanel: "./src/components/starlight/ContentPanel.astro",
				SocialIcons: "./src/components/starlight/SocialIcons.astro",
			},
			
			customCss: ["./src/styles/starlight.css"],

			sidebar: [
				{ label: 'Getting Started', slug: 'getting-started' },
				{ label: 'PokéNav', slug: 'pokenav' },
				{
					label: 'Guides',
					items: [
						// Each item here is one entry in the navigation menu.
						{ label: 'Example Guide', slug: 'guides/example' },
					],
				},
				{
					label: 'Reference',
					items: [{ autogenerate: { directory: 'reference' } }],
				},
			],
		}),
	],
});
