import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2" apply false
	id("io.spring.dependency-management") version "1.1.4" apply false
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22" apply false

	// Lint
    id("com.diffplug.spotless") version "6.25.0" apply false
}

allprojects {
	group = "com.lalala"
	version = "0.0.1-SNAPSHOT"

	tasks.withType<JavaCompile>{
		sourceCompatibility = "21"
		targetCompatibility = "21"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "21"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	repositories {
		mavenCentral()
	}
}

subprojects {
	if (project.name.startsWith("common")) {
		return@subprojects
	}

	apply {
		plugin("kotlin")
		plugin("kotlin-kapt")
		plugin("kotlin-spring")

		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")

		plugin("com.diffplug.spotless")
	}

	dependencies {
		implementation(project(":common-module:common"))
		implementation(project(":common-module:common-reactive"))

		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test")

		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.1.0")
	}

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")

            ktlint()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
}
