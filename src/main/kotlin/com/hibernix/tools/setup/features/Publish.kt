package com.hibernix.tools.setup.features

import com.hibernix.tools.setup.core.DefaultConfigHolder
import com.hibernix.tools.setup.core.LogLevel
import com.hibernix.tools.setup.core.fromDefault
import com.hibernix.tools.setup.core.log
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

data class PublishConfig(
    var description: String? = null,
    var repositoryUrl: String? = null,
    var repositoryUser: String? = System.getenv("SONATYPE_USER"),
    var repositoryPassword: String? = System.getenv("SONATYPE_PASSWORD"),
    var projectUrl: String? = System.getenv("POM_PROJECT_URL"),
    var scmUrl: String? = System.getenv("POM_SCM_URL"),
    var scmConnection: String? = System.getenv("POM_SCM_CONNECTION"),
    var developerName: String? = System.getenv("POM_DEVELOPER_NAME"),
    var developerUrl: String? =System.getenv("POM_DEVELOPER_URL"),
    var licenceName: String = LicenseType.APACHE_2_0.id,
    var licenseUrl: String? = System.getenv("POM_LICENSE_URL"),
    var signingKey: String? = System.getenv("SIGNING_KEY"),
    var signingPassword: String? = System.getenv("SIGNING_PASSWORD"),
) {
    companion object : DefaultConfigHolder<PublishConfig>() {
        override fun newInstance() = PublishConfig()
        override fun copyInstance(instance: PublishConfig) = instance.copy()
    }
}

enum class LicenseType(val id: String) {
    APACHE_2_0("Apache License 2.0"),
    MIT_2_0("MIT 2.0"),
}

fun Features.publish(publishConfig: PublishConfig? = null, description: String? = null) {
    project.setupPublish(publishConfig, description)
}

/**
 * Publish configuration
 */
internal fun Project.setupPublish(publishConfig: PublishConfig? = null, description: String? = null) {

    val config = publishConfig ?: PublishConfig.fromDefault { this.description = description }

    log("Setting up publish: $config")

    plugins.apply("maven-publish")

    val isSigningEnabled = config.signingKey != null

    extensions.configure<PublishingExtension> {
        repositories {
            maven {
                setUrl { config.repositoryUrl }
                credentials {
                    username = config.repositoryUser
                    password = config.repositoryPassword
                }
            }
        }

        publications {
            withType<MavenPublication> {
                if (tasks.findByName("javadocJar") != null) artifact(tasks["javadocJar"])

                pom {
                    name.set(project.name)
                    this.description.set(config.description)
                    url.set(config.projectUrl)

                    licenses {
                        license {
                            name.set(config.licenceName)
                            url.set(config.licenseUrl)
                        }
                    }
                    scm {
                        url.set(config.scmUrl)
                        connection.set(config.scmConnection)
                    }
                    developers {
                        developer {
                            name.set(config.developerName)
                            url.set(config.developerUrl)
                        }
                    }
                }
            }
        }

        if (isSigningEnabled) {
            plugins.apply("signing")

            project.extensions.configure<SigningExtension> {
                useInMemoryPgpKeys(
                    config.signingKey,
                    config.signingPassword,
                )
                sign(publications)
            }
        } else {
            log("Signing of publication disabled!", LogLevel.WARN)
        }
    }
}

fun mavenCentral(repositoryId: String? = null) =
    repositoryId?.let { "https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/${it}/" }
        ?: "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

fun mavenCentralSnapshot() = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
