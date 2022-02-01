val WIN_LIBRARY_PATH =
    "c:\\msys64\\mingw64\\bin;c:\\Tools\\msys64\\mingw64\\bin;C:\\Tools\\msys2\\mingw64\\bin"

plugins {
    kotlin("multiplatform") version "1.6.10"
}

group = "com.omnys"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    val curlPaths = if (isMingwX64) {
        listOf(
            "C:/msys64/mingw64/include/curl",
            "C:/Tools/msys64/mingw64/include/curl",
            "C:/Tools/msys2/mingw64/include/curl"
        )
    } else {
        error("Unknown host name `$hostOs`")
    }

    nativeTarget.apply {
        binaries {
            sharedLib { //build as a shared lib
                baseName = "libwrapper" //lib name
            }
        }

        compilations.getByName("main") {
            cinterops.create("jni") { //loads jni.def, that loads java's jni.h
                packageName = "com.omnys.jni" //This is the package name where jni.h's classes will be available from Kotlin
                val javaHome = File(System.getenv("JAVA_HOME") ?: System.getProperty("java.home"))
                //These are all the folders that will be scanned for jni.h
                includeDirs(
                    Callable { File(javaHome, "include") },
                    Callable { File(javaHome, "include/darwin") },
                    Callable { File(javaHome, "include/linux") },
                    Callable { File(javaHome, "include/win32") }
                )
            }

            cinterops.create("libcurl") {
                defFile = File(projectDir, "src/nativeInterop/cinterop/libcurl.def")
                includeDirs.headerFilterOnly(curlPaths)

                afterEvaluate {
                    if (this.name == "mingwX64") {
                        val winTests =
                            tasks.getByName("mingwX64Test") as org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
                        winTests.environment("PATH", WIN_LIBRARY_PATH)
                    }
                }
            }
        }
    }

    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}
