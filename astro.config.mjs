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
			title: 'CobbleNav Docs',
			description: 'CobbleNav is a sidemod that adds a pokenav item similar to the one featured in the Pokemon RS games, that allows you to check spawns without the command and more in the future',

			social: [
				{ icon: 'github', label: 'GitHub', href: 'https://github.com/MeAlam1/cobblenav' },
				// { icon: 'discord', label: 'Discord', href: '' },
			],

			components: {
				ContentPanel: "./src/components/starlight/ContentPanel.astro",
				SocialIcons: "./src/components/starlight/SocialIcons.astro",
				Footer: "./src/components/starlight/Footer.astro",
			},
			
			customCss: ["./src/styles/starlight.css"],

			sidebar: [
				{ 
					label: 'Getting Started',
					items: [
						{ label: 'Introduction', slug: 'getting-started' },
						{ label: 'Configuration', slug: 'configuration' },
					]
				},
				{
					label: 'Items',
					items: [{ autogenerate: { directory: 'item' } }]
				},
				{
					label: "Addon Development",
					items: [{ autogenerate: { directory: 'addons' } }]
				}
			],
		}),
	],
});
