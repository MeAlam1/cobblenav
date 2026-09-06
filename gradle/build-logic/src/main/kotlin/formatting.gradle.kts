import com.diffplug.spotless.LineEnding

plugins {
	id("com.diffplug.spotless")
}

spotless {
	tasks.withType<Jar>()

	lineEndings = LineEnding.UNIX

	java {
		target("**/*.java")

		targetExclude(
			"**/build/**",
			"**/.gradle/**",
			"**/generated/**",
			"**/run/**",
		)

		leadingSpacesToTabs()
		endWithNewline()
		removeUnusedImports()
		toggleOffOn()

		eclipse("4.31").configFile(rootProject.file("codeformat/formatter-config.xml"))

		importOrder()
	}

	kotlin {
		target("**/*.kt")
		targetExclude(
			"**/build/**",
			"**/.gradle/**",
			"**/generated/**",
			"**/run/**",
			"**/libs/**",
			"**/Accessors*.kt",
		)

		ktlint("1.8.0").editorConfigOverride(
			mapOf(
				"ktlint_standard_no-wildcard-imports" to "disabled",
				"ktlint_standard_package-name" to "disabled",
				"max_line_length" to "160",
			),
		)

		trimTrailingWhitespace()
		leadingSpacesToTabs()
		endWithNewline()
		toggleOffOn()

		bumpThisNumberIfACustomStepChanges(1)
	}
}