cmake_minimum_required (VERSION 2.8.0)
project ("ltp4j")

find_package(JNI)
set (LTP_HOME "${PROJECT_SOURCE_DIR}/ltp" CACHE STRING "Use to specified ltp path") # change it to your ltp root
set (LIBRARY_OUTPUT_PATH    ${PROJECT_SOURCE_DIR}/libs)
set (JNI_SOURCE_DIR         ${PROJECT_SOURCE_DIR}/jni)

include_directories (
    ${JNI_SOURCE_DIR}
    ${LTP_HOME}/include
    ${JNI_INCLUDE_DIRS})

#if(WIN32)
#    include_directories ($ENV{JAVA_HOME}/include/win32)
#else(WIN32)
#    include_directories ($ENV{JAVA_HOME}/include/linux)
#endif(WIN32)

if(APPLE)
    set(CMAKE_CXX_FLAGS "-stdlib=libstdc++")
endif(APPLE)

if(WIN32)
    link_directories (${LTP_HOME}/lib/Debug)
else(WIN32)
    link_directories (${LTP_HOME}/lib)
endif(WIN32)

add_library (split_sentence_jni SHARED
    ${JNI_SOURCE_DIR}/split_sentence_jni.cpp)
target_link_libraries (split_sentence_jni splitsnt)

add_library (segmentor_jni SHARED ${JNI_SOURCE_DIR}/segment_jni.cpp)
target_link_libraries (segmentor_jni segmentor)

add_library(postagger_jni SHARED ${JNI_SOURCE_DIR}/postag_jni.cpp)
target_link_libraries (postagger_jni postagger)

add_library (ner_jni SHARED ${JNI_SOURCE_DIR}/ner_jni.cpp)
target_link_libraries (ner_jni ner)

add_library (parser_jni SHARED ${JNI_SOURCE_DIR}/parser_jni.cpp)
target_link_libraries (parser_jni parser)

add_library (srl_jni SHARED ${JNI_SOURCE_DIR}/srl_jni.cpp)
target_link_libraries (srl_jni srl)

# -----------------------------------------------
# TOOLKIT
