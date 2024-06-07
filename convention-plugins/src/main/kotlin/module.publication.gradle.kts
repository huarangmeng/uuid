import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
}

tasks.withType<Jar> {
    archiveBaseName.set("uuid")
    if (project.name != "kotlinMultiplatform") {
        archiveAppendix.set(project.name)
    }
}

publishing {
    // Configure all publications
    publications.withType<MavenPublication> {
        artifactId = if (name == "kotlinMultiplatform") {
            "uuid"
        } else {
            "uuid-$name"
        }
        groupId = project.group.toString()
        version = project.version.toString()

        println("artifactId = ${this.artifactId}")

        if (project.hasProperty("sonatypeUsername")) {
            println("sonatypeUsername = ${project.property("sonatypeUsername")}")
        }

        // Stub javadoc.jar artifact
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveBaseName.set("uuid")
            archiveClassifier.set("javadoc")
            if (name != "kotlinMultiplatform") {
                archiveAppendix.set(this@withType.name)
            }
        })

        // Provide artifacts information required by Maven Central
        pom {
            name.set("uuid")
            description.set("Kotlin Multiplaform UUID")
            url.set("https://github.com/huarangmeng/uuid")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("huaranmeng")
                    name.set("huaranmeng")
                    organization.set("huaranmeng")
                    organizationUrl.set("https://github.com/huarangmeng")
                }
            }
            scm {
                url.set("https://github.com/huarangmeng/uuid")
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        println("signing.gnupg.keyName = ${project.property("signing.gnupg.keyName")}")
    }
    if (project.hasProperty("signing.gnupg.keyName")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
