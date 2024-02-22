tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":common-module:common"))
    implementation(project(":common-module:common-mvc"))

    // Kotlin Libraries
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Spring Batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")

    // Quartz
    implementation("org.springframework.boot:spring-boot-starter-quartz")

    // Spring JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // kotlin-logging
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
}
