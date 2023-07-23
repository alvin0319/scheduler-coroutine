plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
}

val projectVersion = "1.0-SNAPSHOT"

group = "dev.minjae.pnx.coroutine"
version = projectVersion

repositories {
    mavenCentral()
    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
    maven {
        name = "opencollab-repo-release"
        url = uri("https://repo.opencollab.dev/maven-releases")
    }
    maven {
        name = "opencollab-repo-snapshot"
        url = uri("https://repo.opencollab.dev/maven-snapshots")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("cn.powernukkitx:powernukkitx:1.20.10-r1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    compileOnly(kotlin("stdlib-jdk8"))
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.minjae.dev/snapshots")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "dev.minjae.pnx.coroutine"
            artifactId = "scheduler-coroutine-core"
            version = projectVersion
            from(components["java"])
        }
    }
}
