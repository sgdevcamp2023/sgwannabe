import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_21

allprojects {
	group = "com.lalala"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

tasks.getByName("bootJar") {
	enabled = false
}

subprojects {
	repositories {
		mavenCentral()
	}

	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "application")

	dependencies {
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test")
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
}