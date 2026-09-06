import type { AstroIntegration } from 'astro';

export function starlightSocialIcons(socials: {
	modrinth?: string;
	curseforge?: string;
	maven?: string;
}): AstroIntegration {
	const labelMap = {
		modrinth: 'Modrinth',
		curseforge: 'CurseForge',
		maven: 'Maven',
	}

	return {
		name: 'starlight-social-icon-augment',
		hooks: {
			'astro:config:setup': ({ updateConfig }) => {
				updateConfig({
					vite: {
						plugins: [
							{
								name: 'virtual-config-plugin',
								resolveId(id) {
									if (id === 'virtual:starlight-social-icon-augment') {
										return '\0virtual:starlight-social-icon-augment'; // Prefix with \0 to mark as virtual
									}
								},
								load(id) {
									if (id === '\0virtual:starlight-social-icon-augment') {
										const config = socials;
										return `export default ${JSON.stringify(Object.entries(config).flatMap(([key, value]) => ({ label: labelMap[key as keyof typeof labelMap], href: value, icon: key })))};`;
									}
								},
							}
						]
					}
				})
			},
		},
	};
}

export default starlightSocialIcons;