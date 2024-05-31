import com.android.build.gradle.internal.tasks.factory.dependsOn
import java.nio.file.Files
import java.util.UUID

plugins {
    id("com.android.library")
}

android {
    namespace = "com.androidproject.models"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
    buildFeatures {
        buildConfig = false
    }

    sourceSets {
        getByName("main") {
            assets {
                srcDirs(buildDir.resolve("generated/assets"), buildDir.resolve("generated/assets"))
            }
        }
    }
    project.tasks.preBuild.dependsOn("genUUID")

}

tasks.register("genUUID") {
    val uuid = UUID.randomUUID().toString()
    val odir = file("$buildDir/generated/assets/model-en-us")
    val ofile = file("$odir/uuid")
    doLast {
        Files.createDirectories(odir.toPath())
        ofile.writeText(uuid)
    }
}