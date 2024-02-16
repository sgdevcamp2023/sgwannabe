rootProject.name = "chart"

include(":common-module")
findProject(":common-module")?.projectDir = file("../common-module")

include ("chart-api")
