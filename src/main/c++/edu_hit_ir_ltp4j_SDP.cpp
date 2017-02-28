#include "edu_hit_ir_ltp4j_SDP.h"
#include "lstmsdparser/lstm_sdparser_dll.h"
#include "string_to_jstring.hpp"
#include <vector>
#include <string>
#include <jni.h>

static void * sdparser = NULL;

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_SDP_create
  (JNIEnv * env, jobject obj, jstring model_path) {
  const char * str = env->GetStringUTFChars( model_path , 0);

  if(!sdparser){
    sdparser = lstmsdparser_create_parser(str);
  }

  env->ReleaseStringUTFChars( model_path, str);

  if (sdparser) {
    return 1;
  }

  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_SDP_parse
  (JNIEnv * env, jobject obj, jobject array_words, jobject array_tags, jobject sdp_result) {

  jclass integer = env->FindClass("java/lang/Integer");
  jmethodID integer_construct =env->GetMethodID(integer,"<init>","(I)V");
  jmethodID integer_int =env->GetMethodID(integer,"intValue","()I");

  jclass array_list = env->GetObjectClass(array_words);
  jmethodID list_construct = env->GetMethodID(array_list,"<init>","()V");
  jmethodID list_add = env->GetMethodID(array_list, "add", "(Ljava/lang/Object;)Z");
  jmethodID list_get = env->GetMethodID(array_list, "get", "(I)Ljava/lang/Object;");
  jmethodID list_size = env->GetMethodID(array_list, "size", "()I");
  jmethodID integer_init =env->GetMethodID(integer, "<init>", "(I)V");

  jclass pair = env->FindClass("edu/hit/ir/ltp4j/Pair");
  jmethodID pair_construct = env->GetMethodID(pair, "<init>",
      "(Ljava/lang/Object;Ljava/lang/Object;)V");

  std::vector<std::string> words,tags;

  int size_words = env->CallIntMethod(array_words,list_size);
  int size_tags = env->CallIntMethod(array_tags,list_size);

  if (size_words != size_tags) {
    return -1;
  }

  for (int i = 0; i < size_words; i++) {
    jobject tmp = env->CallObjectMethod(array_words,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    words.push_back(s_s);
    env->ReleaseStringUTFChars(s, st);
  }

  for (int i = 0; i < size_tags; i++) {
    jobject tmp = env->CallObjectMethod(array_tags,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    tags.push_back(s_s);
    env->ReleaseStringUTFChars(s, st);
  }

  std::vector<std::vector<std::pair<int, std::string>>> hyp;
  int len = lstmsdparser_parse(sdparser, words, tags, hyp);

  if (len <= 0) {
    return -1;
  }

  for(size_t i = 0; i < hyp.size(); ++i) {
    jobject sems = env->NewObject(array_list,list_construct);
    for (size_t j = 0; j < hyp[i].size(); ++j) {
      jobject semparent = env->NewObject(integer,integer_construct,hyp[i][j].first);
      jobject semrelate = stringToJstring(env,hyp[i][j].second.c_str());

      jobject sem = env->NewObject(pair,pair_construct,semparent,semrelate);
      env->CallBooleanMethod(sems, list_add, sem);
    }
    env->CallBooleanMethod(sdp_result,list_add,sems);
  }

  return (int)hyp.size();
}

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_SDP_release
  (JNIEnv * env, jobject obj) {
  lstmsdparser_release_parser(sdparser);
  sdparser = NULL;
}
