plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("repositories-plugin") {
            id = "repositories"
            implementationClass = "RepositoriesPlugin"
        }
    }
}
