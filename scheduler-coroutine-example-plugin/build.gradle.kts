plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.minjae.pnx.coroutine"
version = "1.0-SNAPSHOT"

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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":scheduler-coroutine-core"))
}

tasks.test {
    useJUnitPlatform()
}
