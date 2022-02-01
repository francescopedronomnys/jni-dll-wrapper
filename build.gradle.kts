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

    nativeTarget.apply {
        binaries {
            sharedLib { //build as a shared lib
                baseName = "libwrapper" //lib name
            }
        }

       /* compilations.getByName("main") {
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
        }*/
    }

   /* sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }*/
}
