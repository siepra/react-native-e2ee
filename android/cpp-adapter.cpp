#include <jni.h>
#include "react-native-e2ee.h"

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_e2ee_E2eeModule_nativeMultiply(JNIEnv *env, jclass type, jdouble a, jdouble b) {
    return e2ee::multiply(a, b);
}
