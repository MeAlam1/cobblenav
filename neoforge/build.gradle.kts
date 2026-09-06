plugins {
    alias(libs.plugins.cobblenav.convention.common)
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

configurations {
    getByName("developmentNeoForge").extendsFrom(common.get())
}

repositories {
    maven {
        name = "NeoForged"
        url = uri("https://maven.neoforged.net/releases")
    }
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

dependencies {
    neoForge(libs.neoforge)

    shadowBundle(projects.common) {
        targetConfiguration = "transformProductionNeoForge"
    }
	
	implementation(libs.neoforge.kotlin) {
		exclude(group = "net.neoforged.fancymodloader", module = "loader")
	}
	
	modImplementation(libs.bundles.neoforge) {
		isTransitive = false
	}
	modCompileOnly(libs.bundles.neoforge.compileOnly) {
		isTransitive = false
	}
	modRuntimeOnly(libs.bundles.neoforge.runtimeOnly) {
		isTransitive = false
	}
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }
}

