import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    id("com.palantir.docker-run") version "0.22.1"
    id("com.palantir.docker") version "0.22.1"
}

group = "com.github.nwillc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

docker {
    name = "multi-db:1.0"
    files("src/main/docker/postgresql/create-databases.sh")
    setDockerfile(File("src/main/docker/postgresql/Dockerfile"))
}

dockerRun {
    name = "postgresql"
    image = "multi-db:1.0"
    ports("5432:5432")
    env(mutableMapOf(
        "POSTGRES_PASSWORD" to "example",
        "POSTGRES_USER" to "postgres",
        "POSTGRES_MULTIPLE_DATABASES" to "payment-service party-service"
    ))
}

tasks {
    named("dockerRun") {
        dependsOn("docker")
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
