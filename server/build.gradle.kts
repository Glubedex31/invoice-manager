plugins {
    id("java")
    id("application")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("server.Main")
}

group = "server"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.1")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.1")

    implementation(project(":commons"))

    runtimeOnly("com.h2database:h2:1.4.200")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.5.1")
}

tasks.test {
    useJUnitPlatform()
}
