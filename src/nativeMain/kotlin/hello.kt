package com.omnys.jni.native

import kotlinx.cinterop.CPointer
import com.omnys.jni.JNIEnvVar
import com.omnys.jni.jclass
import com.omnys.jni.jint

@CName("Java_com_omnys_jni_java_NativeHost_callIncrementInt")
fun callIncrementInt(env: CPointer<JNIEnvVar>, clazz: jclass, x: jint): jint {
    return x + 1
}

/*
class Hello {
    fun ciao(): Int {
        return 1
    }
}*/
