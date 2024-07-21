plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.11"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("client.Main")
}

group = "client"
version = "0.0.1-SNAPSHOT"

javafx {
    version = "21.0.2"
    modules = listOf(
            "javafx.controls",
            "javafx.fxml"
    )
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("org.glassfish.jersey.core:jersey-client:3.0.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.1")

    implementation("com.google.inject:guice:7.0.0")
    implementation("org.glassfish.jersey.inject:jersey-hk2:3.0.2")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:3.0.3")
    implementation("org.openjfx:javafx-controls:21.0.2")
    implementation("org.openjfx:javafx-fxml:21.0.2")

    implementation(project(":commons"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
