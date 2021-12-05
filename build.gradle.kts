plugins {
    java
    // id("me.champeau.jmh") version "0.6.6"
    `maven-publish`
    signing
}

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    implementation("org.apache.logging.log4j:log4j-api:2.1")
}


group = "io.github.mysto"
version = "0.9"

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "io.github.mysto"
            artifactId = "ff3"
            version = "0.9"

            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("ff3")
                description.set("A Format-preserving encryption library for FF3-1")
                url.set("http://privacylogistics.com")
                properties.set(mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                ))
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("bschoeni")
                        name.set("Brad Schoening")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/mysto/java-fpe.git")
                    developerConnection.set("scm:git:ssh://github.com/mysto/java-fpe.git")
                    url.set("http://github.com/mysto/java-fpe/")
                }
            }
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos"))
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

