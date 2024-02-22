tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":common-module:common"))
    implementation(project(":common-module:common-reactive"))

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Spring WebFlux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.4")

    // Reactor
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    testImplementation("io.projectreactor:reactor-test")

    // Eureka
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:4.1.0")

    // Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Open Tracing
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.zipkin.reporter2:zipkin-reporter-brave")

    // Logback for LogStash
    implementation("net.logstash.logback:logstash-logback-encoder:6.3")
}
