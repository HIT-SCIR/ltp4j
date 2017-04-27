#include "segmentor/segment_dll.h"
#include "edu_hit_ir_ltp4j_Segmentor.h"
#include "string_to_jstring.hpp"
#include <iostream>
#include <string>
#include <vector>

static void* segmentor = NULL;
static bool using_customized_model = false;

inline void foo(){
  if(using_customized_model)
    customized_segmentor_release_segmentor(segmentor);
  else
    segmentor_release_segmentor(segmentor);
  using_customized_model = false;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Segmentor_create__Ljava_lang_String_2
  (JNIEnv* env, jobject obj, jstring model_path) {
  const char* str = env->GetStringUTFChars( model_path , 0);

  if(!segmentor){
    segmentor = segmentor_create_segmentor(str);
  } else{
    foo();
    segmentor = segmentor_create_segmentor(str);
  }

  env->ReleaseStringUTFChars( model_path, str);

  if(segmentor) {
    using_customized_model = false;
    return 1;
  }

  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Segmentor_create__Ljava_lang_String_2Ljava_lang_String_2
  (JNIEnv* env, jobject obj, jstring model_path, jstring lexicon_path) {

  const char* str_model = env->GetStringUTFChars( model_path , 0);
  const char* str_lexicon = env->GetStringUTFChars( lexicon_path , 0);

  if(!segmentor){
    segmentor = segmentor_create_segmentor(str_model,str_lexicon);
  } else{
    foo();
    segmentor = segmentor_create_segmentor(str_model,str_lexicon);
  }

  env->ReleaseStringUTFChars( model_path, str_model);
  env->ReleaseStringUTFChars( lexicon_path, str_lexicon);

  if(segmentor) {
    using_customized_model = false;
    return 1;
  }
  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Segmentor_create__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2
  (JNIEnv* env, jobject obj, jstring baseline_model_path, jstring customized_model_path, jstring lexicon_path) {

  const char* str_baseline_model = env->GetStringUTFChars( baseline_model_path, 0);
  const char* str_customized_model = env->GetStringUTFChars( customized_model_path, 0);
  const char* str_lexicon = env->GetStringUTFChars( lexicon_path , 0);

  if(!segmentor){
    segmentor = customized_segmentor_create_segmentor(str_baseline_model, str_customized_model, str_lexicon);
  } else{
    foo();
    segmentor = customized_segmentor_create_segmentor(str_baseline_model, str_customized_model, str_lexicon);
  }

  env->ReleaseStringUTFChars( baseline_model_path, str_baseline_model);
  env->ReleaseStringUTFChars( customized_model_path, str_customized_model);
  env->ReleaseStringUTFChars( lexicon_path, str_lexicon);

  if(segmentor) {
    using_customized_model = true;
    return 1;
  }
  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Segmentor_segment
  (JNIEnv* env, jobject obj, jstring sent, jobject array_words) {

  jclass array_list = env->GetObjectClass(array_words);
  jmethodID list_add = env->GetMethodID(array_list, "add", "(Ljava/lang/Object;)Z");

  const char* str_sent = env->GetStringUTFChars( sent , 0);
  std::string sentence(str_sent);
  std::vector<std::string> words;

  int len = segmentor_segment(segmentor, sentence, words);

  for(int i = 0; i < len; i++) {
    jobject tmp = stringToJstring(env,words[i].c_str());
    env->CallBooleanMethod(array_words,list_add,tmp);
  }
  env->ReleaseStringUTFChars(sent, str_sent);
  return len;
}

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_Segmentor_release
  (JNIEnv* env, jobject obj) {
  foo();
  segmentor = NULL;
}


