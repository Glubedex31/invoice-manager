plugins {
    id("java")

    id("org.openjfx.javafxplugin") version "0.0.10"
    application
    kotlin("jvm") version "1.6.10"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "client"
version = "0.0.1-SNAPSHOT"

javafx {
    version = "17.0.1"
    modules = listOf(
            "javafx.controls",
            "javafx.fxml"
    )
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.inject:guice:5.0.1")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}