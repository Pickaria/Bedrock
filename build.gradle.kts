import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.20"
	`maven-publish`
	java
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

group = "fr.pickaria"
version = "1.0.21"

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
	compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/Pickaria/Bedrock")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}

	publications {
		create<MavenPublication>("maven") {
			artifactId = "bedrock"
			from(components["java"])
		}
	}
}