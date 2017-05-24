#include "postagger/postag_dll.h"
#include "edu_hit_ir_ltp4j_Postagger.h"
#include "string_to_jstring.hpp"
#include <vector>
#include <string>
#include <iostream>
#include <jni.h>

static void * postagger = NULL;

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Postagger_create__Ljava_lang_String_2
  (JNIEnv * env, jobject obj, jstring model_path) {
  const char* str = env->GetStringUTFChars( model_path , 0);

  if(!postagger){
    postagger = postagger_create_postagger(str);
  } else {
    postagger_release_postagger(postagger);
    postagger = postagger_create_postagger(str);
  }

  env->ReleaseStringUTFChars( model_path, str);

  if (postagger) {
    return 1;
  }
  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Postagger_create__Ljava_lang_String_2Ljava_lang_String_2
  (JNIEnv * env, jobject obj, jstring model_path, jstring lexicon_path) {

  const char * model = env->GetStringUTFChars( model_path , 0);
  const char * lexicon = env->GetStringUTFChars( lexicon_path , 0);

  if(!postagger){
    postagger = postagger_create_postagger(model,lexicon);
  } else {
    postagger_release_postagger(postagger);
    postagger = postagger_create_postagger(model,lexicon);
  }

  env->ReleaseStringUTFChars( model_path, model);
  env->ReleaseStringUTFChars( lexicon_path, lexicon);

  if(postagger) {
    return 1;
  }
  return -1;
}


JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Postagger_postag
  (JNIEnv * env, jobject obj, jobject array_words, jobject array_postags) {
  jclass array_list = env->GetObjectClass(array_words);

  jmethodID list_add = env->GetMethodID(array_list, "add", "(Ljava/lang/Object;)Z");
  jmethodID list_get = env->GetMethodID(array_list, "get", "(I)Ljava/lang/Object;");
  jmethodID list_size = env->GetMethodID(array_list, "size", "()I");

  std::vector<std::string> words, postags;

  int size = env->CallIntMethod(array_words,list_size);

  for (int i = 0; i < size ; i++) {
    jobject tmp = env->CallObjectMethod(array_words,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    words.push_back(s_s);
    env->ReleaseStringUTFChars( s, st);
  }

  int len = postagger_postag(postagger,words,postags);

  for (int i = 0; i < len; i++) {
    jobject tmp = stringToJstring(env,postags[i].c_str());
    env->CallBooleanMethod(array_postags,list_add,tmp);
  }

  return len;
}

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_Postagger_release
  (JNIEnv * env, jobject obj) {
  postagger_release_postagger(postagger);
  postagger = NULL;
}



