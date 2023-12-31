import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

group = "dev.minjae.pnx.coroutine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
