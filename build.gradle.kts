import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.9.22"
	`maven-publish`
	java
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

group = "fr.pickaria"
version = "1.1.2"

repositories {
	mavenCentral()
	maven("https://jitpack.io")
	maven("https://oss.sonatype.org/content/groups/public/")
	maven("https://repo.papermc.io/repository/maven-public/")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

dependencies {
	implementation(kotlin("stdlib"))
	implementation(kotlin("reflect"))
	compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
	compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

publishing {
	repositories {
		maven {
			name = "pickariaRepositoryReleases"
			url = uri("https://maven.pickaria.fr/releases")
			credentials {
				username = System.getenv("MAVEN_USERNAME") ?: property("mavenUser").toString()
				password = System.getenv("MAVEN_PASSWORD") ?: property("mavenPassword").toString()
			}
			authentication {
				create<BasicAuthentication>("basic")
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