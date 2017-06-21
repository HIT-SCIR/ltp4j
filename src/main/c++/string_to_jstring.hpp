#include <cstdlib>
#include <cstring>
#include <jni.h>

inline jstring stringToJstring(JNIEnv* env, const char* pat) {
  jclass strClass = env->FindClass("Ljava/lang/String;");
  jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
  jbyteArray bytes = env->NewByteArray( (jsize)strlen(pat) );
  env->SetByteArrayRegion(bytes, 0, (jsize)strlen(pat), (jbyte*)pat);
  jstring encoding = env->NewStringUTF("utf-8");  
  return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}
