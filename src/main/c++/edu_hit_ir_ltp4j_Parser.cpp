#include "edu_hit_ir_ltp4j_Parser.h"
#include "parser/parser_dll.h"
#include "string_to_jstring.hpp"
#include <vector>
#include <string>
#include <iostream>
#include <jni.h>

static void * parser = NULL;

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Parser_create
  (JNIEnv * env, jobject obj, jstring model_path) {

  const char * str = env->GetStringUTFChars( model_path , 0);

  if(!parser){
    parser = parser_create_parser(str);
  }

  env->ReleaseStringUTFChars( model_path, str);

  if(parser) {
    return 1;
  }

  return -1;
}

JNIEXPORT jint JNICALL Java_edu_hit_ir_ltp4j_Parser_parse
  (JNIEnv * env, jobject obj, jobject array_words, jobject array_tags, jobject array_heads, jobject array_deprels) {

  jclass array_list = env->GetObjectClass(array_words);
  jclass integer = env->FindClass("java/lang/Integer");

  jmethodID list_add = env->GetMethodID(array_list, "add", "(Ljava/lang/Object;)Z");
  jmethodID list_get = env->GetMethodID(array_list, "get", "(I)Ljava/lang/Object;");
  jmethodID list_size = env->GetMethodID(array_list, "size", "()I");
  jmethodID integer_init =env->GetMethodID(integer, "<init>", "(I)V");

  std::vector<std::string> words,tags,deprels;
  std::vector<int> heads;

  int size_words = env->CallIntMethod(array_words,list_size);
  int size_tags = env->CallIntMethod(array_tags,list_size);

  if (size_words!=size_tags) {
    return -1;
  }

  for (int i = 0; i < size_words; i++) {
    jobject tmp = env->CallObjectMethod(array_words,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    words.push_back(s_s);
    env->ReleaseStringUTFChars( s, st);
  }

  for (int i = 0; i < size_tags; i++) {
    jobject tmp = env->CallObjectMethod(array_tags,list_get,i);
    jstring s = reinterpret_cast<jstring> (tmp);
    const char * st = env->GetStringUTFChars(s,0);
    std::string s_s(st);
    tags.push_back(s_s);
    env->ReleaseStringUTFChars( s, st); 
  }

  int len = parser_parse(parser,words,tags,heads,deprels);

  if (len < 0) {
    return -1;
  }

  size_t size = heads.size();
  for (size_t i = 0; i<size; i++){
    jobject integer_object = env->NewObject(integer,integer_init,heads.at(i));
    env->CallBooleanMethod(array_heads,list_add, integer_object);
  }

  for (size_t i = 0;i<size;i++){
    jobject tmp =  stringToJstring(env,deprels[i].c_str());
    env->CallBooleanMethod(array_deprels,list_add,tmp);
  }

  return len;
}

JNIEXPORT void JNICALL Java_edu_hit_ir_ltp4j_Parser_release
  (JNIEnv * env, jobject obj) {
  parser_release_parser(parser);
  parser = NULL;
}

