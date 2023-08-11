rootProject.name="txwithr2dbc"

include(
    "main",
    "domain"
)

// DRIVER
include(
    "http-server"
)
project(":http-server").projectDir = file("driver/http-server")

// DRIVEN
include(
    "external",
    "persistence"
)
project(":external").projectDir = file("driven/external")
project(":persistence").projectDir = file("driven/persistence")
