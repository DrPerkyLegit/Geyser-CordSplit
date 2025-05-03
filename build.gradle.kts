plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1" // add shadow plugin
}

val id = project.property("id") as String
val extensionName = project.property("name") as String
val geyserApiVersion = "2.6.1"
val adventureVersion = "4.20.0"

repositories {
    // Repo for the Geyser API artifact
    maven("https://repo.opencollab.dev/main/")

    // Add other repositories here
    mavenCentral()
}

dependencies {
    // Geyser API - needed for all extensions
    compileOnly("org.geysermc.geyser:api:$geyserApiVersion-SNAPSHOT")

    // Include other dependencies here - e.g. configuration libraries.
    compileOnly("org.geysermc.geyser:core:$geyserApiVersion-SNAPSHOT")

    implementation("org.geysermc.mcprotocollib:protocol:1.21.5-SNAPSHOT")

    implementation(platform("net.kyori:adventure-bom:$adventureVersion"))
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-minimessage:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-plain:$adventureVersion")
}

// Java currently requires Java 17 or higher, so extensions should also target it
java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

afterEvaluate {
    val idRegex = Regex("[a-z][a-z0-9-_]{0,63}")
    if (idRegex.matches(id).not()) {
        throw IllegalArgumentException("Invalid extension id $id! Must only contain lowercase letters, " +
                "and cannot start with a number.")
    }

    val nameRegex = Regex("^[A-Za-z_.-]+$")
    if (nameRegex.matches(extensionName).not()) {
        throw IllegalArgumentException("Invalid extension name $extensionName! Must fit regex: ${nameRegex.pattern})")
    }
}

tasks {
    // This automatically fills in the extension.yml file.
    processResources {
        filesMatching("extension.yml") {
            expand(
                "id" to id,
                "name" to extensionName,
                "api" to geyserApiVersion,
                "version" to project.version,
                "author" to project.property("author")
            )
        }
    }
}


tasks.shadowJar {
    mergeServiceFiles()
    //relocate("net.kyori", "org.geyser.extension.shaded.kyori")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}