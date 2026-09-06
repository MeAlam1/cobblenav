plugins {
    alias(libs.plugins.cobblenav.convention.common)
}

architectury {
    platformSetupLoomIde()
    fabric()
}

repositories {
	maven(url = "https://maven.terraformersmc.com/") // For Mod Menu
}

configurations {
    getByName("developmentFabric").extendsFrom(common.get())
}

dependencies {
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.fabric.api)
	
	modImplementation(libs.bundles.fabric) {
		isTransitive = false
	}
	modCompileOnly(libs.bundles.fabric.compileOnly) {
		isTransitive = false
	}
	modRuntimeOnly(libs.bundles.fabric.runtimeOnly) {
		isTransitive = false
	}

    shadowBundle(projects.common) {
        targetConfiguration = "transformProductionFabric"
        isTransitive = false
    }

	// Fix for Cobblemon dev on Fabric
	modRuntimeOnly("org.graalvm.js:js:22.3.0")
	modRuntimeOnly("org.graalvm.sdk:graal-sdk:22.3.0")
	modRuntimeOnly("org.graalvm.regex:regex:22.3.0")
	modRuntimeOnly("org.graalvm.truffle:truffle-api:22.3.0")
	modRuntimeOnly("com.ibm.icu:icu4j:71.1")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}

