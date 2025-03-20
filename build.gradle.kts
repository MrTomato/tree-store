plugins {
    idea
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jooq.jooq-codegen-gradle") version "3.19.19" //matching version for the one included in the spring starter
    id("org.liquibase.gradle") version "2.2.0" //matching version for the one included in the spring starter
}

group = "ai.prewave"
version = "0.9.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

repositories {
    mavenCentral()
}

val postgresDockerImage = "postgres:latest"
val postgresDriverDependency = "org.postgresql:postgresql:42.7.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-security:3.4.3")
    implementation("org.springframework.boot:spring-boot-starter-web:3.4.3")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    developmentOnly("org.springframework.boot:spring-boot-devtools:3.4.3")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.4.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.10")
    implementation(postgresDriverDependency)

    //testing dependencies
    testImplementation("io.mockk:mockk:1.13.17")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.4.3")
    testImplementation("org.springframework.security:spring-security-test:6.4.3")
    testImplementation("org.springframework.boot:spring-boot-testcontainers:3.4.3")

    testImplementation("org.testcontainers:postgresql:1.20.6")
    testImplementation("org.testcontainers:junit-jupiter:1.20.6")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:2.1.10")

    testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
    testImplementation("io.kotest:kotest-assertions-json-jvm:5.9.1")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:2.0.2")


    //dependencies for liquibase plugin
    liquibaseRuntime("org.liquibase:liquibase-core")
    liquibaseRuntime("info.picocli:picocli:4.7.5")
    liquibaseRuntime(postgresDriverDependency)

    //dependencies for jooq plugin
    jooqCodegen(postgresDriverDependency)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.bootRun.configure {
    systemProperty("spring.profiles.active", "local")
}
tasks.withType<Test>().configureEach {
    doFirst {
        systemProperty("spring.profiles.active", "test")
    }
    useJUnitPlatform()
}

val changelogPath = "src/main/resources/db/changelog/db.changelog-master.xml"
val dbDriver = "org.postgresql.Driver"
val dbName = "mydb"
val dbUser = "testuser"
val dbPassword = "testpassword"

buildscript {
    dependencies {
        //cannot access global variables from in here -> cannot extract string :(
        classpath("org.testcontainers:postgresql:1.20.6")
    }
}

val postgres = org.testcontainers.containers.PostgreSQLContainer<Nothing>(postgresDockerImage).apply {
    withDatabaseName(dbName)
    withUsername(dbUser)
    withPassword(dbPassword)
    start()
}

liquibase {
    activities.register("main") {
        this.arguments = mapOf(
            "changeLogFile" to changelogPath,
            "url" to postgres.jdbcUrl,
            "username" to postgres.username,
            "password" to postgres.password,
            "driver" to dbDriver
        )
    }
}


tasks.register("postgresContainer") {
    tasks["jooqCodegen"]
}


jooq {
    configuration {
        jdbc {
            driver = dbDriver
            url = postgres.jdbcUrl
            user = postgres.username
            password = postgres.password
        }

    }
}

tasks.named("jooqCodegen") {
    dependsOn(tasks.named("update"))
}
tasks.named("compileKotlin") {
    dependsOn(tasks.named("jooqCodegen"))
}
