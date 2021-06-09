import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version kotlinVersion
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

group = "realworld"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(Deps.kotlinLib)
    implementation(Deps.ktorServerNetty)
    implementation(Deps.logback)
    implementation(Deps.ktorAuthJwt)
    implementation("mysql", "mysql-connector-java", "8.0.25")
    implementation(Deps.ktorJackson)
    implementation(Deps.h2Database)
    implementation(Deps.exposed)
    implementation(Deps.hikari)
    testImplementation(Deps.ktorTests)
}
