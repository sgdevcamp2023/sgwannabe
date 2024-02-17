rootProject.name = "chart"

include ("chart-api")


include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")

include(":common-module:common")
findProject(":common-module:common")?.projectDir = file("../common-module/common")

include(":common-module:common-mvc")
findProject(":common-module:common-mvc")?.projectDir = file("../common-module/common-mvc")

include(":common-module:common-reactive")
findProject(":common-module:common-reactive")?.projectDir = file("../common-module/common-reactive")
