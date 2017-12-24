#include "edu_hit_ir_ltp4j_SplitSentence.h"
#include "splitsnt/SplitSentence.h"
#include "string_to_jstring.hpp"
#include <string>
#include <iostream>
#include <vector>
#include <jni.h>

using namespace std;

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_SplitSentence_splitSentence
  (JNIEnv * env, jobject obj, jstring sent, jobject array_sents){
  const char * str = env->GetStringUTFChars(sent,0);
  string s_s(str);

  jclass array_list = env->GetObjectClass(array_sents);
  jmethodID list_add = env->GetMethodID(array_list,"add","(Ljava/lang/Object;)Z");

  vector<string> sents;
  SplitSentence(s_s,sents);

  for (unsigned i = 0; i < sents.size(); i++) {
    jobject tmp = stringToJstring(env,sents[i].c_str());
    env->CallBooleanMethod(array_sents,list_add,tmp);
  }

  env->ReleaseStringUTFChars(sent,str);
}
