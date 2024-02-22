plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "chart"

include("chart-api")
include("chart-batch")

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")

include(":common-module:common")
findProject(":common-module:common")?.projectDir = file("../common-module/common")

include(":common-module:common-mvc")
findProject(":common-module:common-mvc")?.projectDir = file("../common-module/common-mvc")

include(":common-module:common-reactive")
findProject(":common-module:common-reactive")?.projectDir = file("../common-module/common-reactive")
include("chart-consumer")
