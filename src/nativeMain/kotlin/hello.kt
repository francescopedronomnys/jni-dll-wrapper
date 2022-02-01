package com.omnys.jni.native

import kotlinx.cinterop.CPointer
import com.omnys.jni.JNIEnvVar
import com.omnys.jni.jclass
import com.omnys.jni.jint
import libcurl.*

@CName("Java_com_omnys_jni_java_NativeHost_callIncrementInt")
fun callIncrementInt(env: CPointer<JNIEnvVar>, clazz: jclass, x: jint): jint {
    return x + 1
}

@CName("Java_com_omnys_jni_java_NativeHost_curlGet")
fun curlGet(env: CPointer<JNIEnvVar>, clazz: jclass) {
    val curl = curl_easy_init()  ?: throw RuntimeException("Could not initialize an easy handle")


    curl_easy_setopt(curl, CURLOPT_URL, "http://jonnyzzz.com")
    curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L)
    val res = curl_easy_perform(curl)
    if (res != CURLE_OK) {
        //println("curl_easy_perform() failed ${curl_easy_strerror(res)?.toKString()}")
    } else {
        println("curl_easy_perform() ok!")
    }
    curl_easy_cleanup(curl)
}

/*
class Hello {
    fun ciao(): Int {
        return 1
    }
}*/
