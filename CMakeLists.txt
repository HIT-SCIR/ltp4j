cmake_minimum_required (VERSION 2.8.0)
project ("ltp4j")

find_package(JNI)
set (LTP_HOME "/path/to/your/ltp/" CACHE STRING "Use to specified ltp path")
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

#if(WIN32)
#    link_directories (${LTP_HOME}/lib/Debug)
#else(WIN32)
#    link_directories (${LTP_HOME}/lib)
#endif(WIN32)

#link_directories (${LTP_HOME}/lib)

add_library (split_sentence_jni SHARED
    ${JNI_SOURCE_DIR}/split_sentence_jni.cpp)
add_library (segmentor_jni SHARED
    ${JNI_SOURCE_DIR}/segment_jni.cpp)
add_library(postagger_jni SHARED
    ${JNI_SOURCE_DIR}/postag_jni.cpp)
add_library (ner_jni SHARED
    ${JNI_SOURCE_DIR}/ner_jni.cpp)
add_library (parser_jni SHARED
    ${JNI_SOURCE_DIR}/parser_jni.cpp)
add_library (srl_jni SHARED
    ${JNI_SOURCE_DIR}/srl_jni.cpp)

if (WIN32)
    target_link_libraries (split_sentence_jni
        debug       ${LTP_HOME}/lib/Debug/splitsnt.lib
        optimized   ${LTP_HOME}/lib/Release/splitsnt.lib)
    target_link_libraries (segmentor_jni
        debug       ${LTP_HOME}/lib/Debug/segmentor.lib
        optimized   ${LTP_HOME}/lib/Release/segmentor.lib)
    target_link_libraries (postagger_jni
        debug       ${LTP_HOME}/lib/Debug/postagger.lib
        optimized   ${LTP_HOME}/lib/Release/postagger.lib)
    target_link_libraries (ner_jni
        debug       ${LTP_HOME}/lib/Debug/ner.lib
        optimized   ${LTP_HOME}/lib/Release/ner.lib)
    target_link_libraries (parser_jni
        debug       ${LTP_HOME}/lib/Debug/parser.lib
        optimized   ${LTP_HOME}/lib/Release/parser.lib)
    target_link_libraries (srl_jni
        debug       ${LTP_HOME}/lib/Debug/srl.lib
        optimized   ${LTP_HOME}/lib/Release/srl.lib)
else (WIN32)
    target_link_libraries (split_sentence_jni   ${LTP_HOME}/lib/splitsnt)
    target_link_libraries (segmentor_jni        ${LTP_HOME}/lib/segmentor)
    target_link_libraries (postagger_jni        ${LTP_HOME}/lib/postagger)
    target_link_libraries (ner_jni              ${LTP_HOME}/lib/ner)
    target_link_libraries (parser_jni           ${LTP_HOME}/lib/parser)
    target_link_libraries (srl_jni              ${LTP_HOME}/lib/srl)
endif (WIN32)


# -----------------------------------------------
# TOOLKIT
