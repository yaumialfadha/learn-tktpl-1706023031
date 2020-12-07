//
// Created by yaumi on 07/12/20.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_yaumialfadha_MainActivity_stingFROMJNI(
    JNIEnv *env,
    jobject /*this */){
    std::string hello = "Hello yaumi";
    return env->NewStringUTF(hello.c_str());
  }

