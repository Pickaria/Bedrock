import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.20"
	`maven-publish`
	java
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

group = "fr.pickaria"
version = "1.0.12-SNAPSHOT"

repositories {
	mavenCentral()
	maven("https://jitpack.io")
	maven("https://oss.sonatype.org/content/groups/public/")
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://maven.quozul.dev/snapshots")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(kotlin("reflect"))
	compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
	compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}

publishing {
	repositories {
		maven {
			url = uri("https://maven.quozul.dev/snapshots")
			credentials(PasswordCredentials::class)
		}
	}

	publications {
		create<MavenPublication>("maven") {
			groupId = "fr.pickaria"
			artifactId = "bedrock"
			version = "1.0.12-SNAPSHOT"

			from(components["java"])
		}
	}
}