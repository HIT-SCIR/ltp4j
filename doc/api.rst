编程接口
========

.. java:package:: edu.hit.ir.ltp4j

分词接口
--------

.. java:type:: public class Segmentor

分词主要提供三个接口：

.. java:method:: public final native int create(String modelPath)

    功能：

    读取模型文件，初始化分词器。

    参数：

    +---------------------+------------------------------------------------------------+
    | 参数名              | 参数描述                                                   |
    +=====================+============================================================+
    | String modelPath    | 指定模型文件的路径                                         |
    +---------------------+------------------------------------------------------------+
   

.. java:method:: public final native void release()

    功能：

    释放模型文件，销毁分词器。

.. java:method:: public final native int segment(String sent, List<String> words)

    功能：

    调用分词接口。

    参数：

    +---------------------+------------------------------------------------------------+
    | 参数名              | 参数描述                                                   |
    +=====================+============================================================+
    | String sent         | 待分词句子                                                 |
    +---------------------+------------------------------------------------------------+
    | List<String> words  | 结果分词序列                                               |
    +---------------------+------------------------------------------------------------+


**示例程序**

.. code:: java

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;

    public class TestSegment {
      public static void main(String[] args) {
        if(Segmentor.create("../../../ltp_data/cws.model")<0){
          System.err.println("load failed");
          return;
        }

        String sent = "我是中国人";
        List<String> words = new ArrayList<String>();
        int size = Segmentor.segment(sent,words);

        for(int i = 0; i<size; i++) {
          System.out.print(words.get(i));
          if(i==size-1) {
            System.out.println();
          } else{  
            System.out.print("\t");
          }
        }
        Segmentor.release();
      }
    }


词性标注接口
--------------

词性标注主要提供四个接口

.. java:method:: public final native int create(String modelPath)

    功能：

    读取模型文件，初始化词性标注器

    参数：

    +---------------------------------+--------------------------------------------------------------------+
    | 参数名                          | 参数描述                                                           |
    +=================================+====================================================================+
    | String modelPath                | 词性标注模型路径                                                   |
    +---------------------------------+--------------------------------------------------------------------+
    
    
.. java:method:: public final native int create(String modelPath, String lexiconPath)

    功能：

    读取模型文件，初始化词性标注器

    参数：
        
    +---------------------------+---------------------------+
    | 参数名                    | 参数描述                  |
    +===========================+===========================+
    | String modelPath          | 词性标注模型路径          |
    +---------------------------+---------------------------+
    | String lexiconPath        | 指定词性标注外部词典路径。|
    +---------------------------+---------------------------+
    lexiconPath参数指定的外部词典文件样例如下所示。每行指定一个词，第一列指定单词，第二列之后指定该词的候选词性（可以有多项，每一项占一列），列与列之间用空格区分::

        雷人 v a
        】 wp

        
.. java:method:: public final native void release()

    功能：

    释放模型文件，销毁分词器。


.. java:method:: public final native int postag(List<String> words, List<String> tags)

    功能：

    调用词性标注接口

    参数：

    +--------------------+--------------------------------------------------------------------+
    | 参数名             | 参数描述                                                           |
    +====================+====================================================================+
    | List<String> words | 待标注的词序列                                                     |
    +--------------------+--------------------------------------------------------------------+
    | List<String> tags  | 词性标注结果，序列中的第i个元素是第i个词的词性                     |
    +--------------------+--------------------------------------------------------------------+

**示例程序**

.. code:: java

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;

    public class TestPostag {
      public static void main(String[] args) {
        if(Postagger.create("../../../ltp_data/pos.model")<0) {
          System.err.println("load failed");
          return;
        }

        List<String> words= new ArrayList<String>();
        words.add("我");   words.add("是");
        words.add("中国"); words.add("人");
        List<String> postags= new ArrayList<String>();

        int size = Postagger.postag(words,postags);
        for(int i = 0; i < size; i++) {
          System.out.print(words.get(i)+"_"+postags.get(i));
          if(i==size-1) {
            System.out.println();
          } else {
            System.out.print("|");
          }
        }
        Postagger.release();
      }
    }


命名实体识别接口
------------------

.. java:type:: public class NER

命名实体识别主要提供三个接口：

.. java:method:: public final native int create(String modelPath)

    功能：

    读取模型文件，初始化命名实体识别器

    参数：

    +----------------------------------------+--------------------------------------------------------------------+
    | 参数名                                 | 参数描述                                                           |
    +========================================+====================================================================+
    | const char * path                      | 命名实体识别模型路径                                               |
    +----------------------------------------+--------------------------------------------------------------------+

    返回值：

    返回一个指向词性标注器的指针。

.. java:method:: public final native void release()

    功能：

    释放模型文件，销毁命名实体识别器。


.. java:method:: public final native int recognize(List<String> words, List<String> postags, List<String> ners)

    功能：

    调用命名实体识别接口

    参数：

    +----------------------+----------------------------------------------------------------------------------------+
    | 参数名               | 参数描述                                                                               |
    +======================+========================================================================================+
    | List<String> words   | 待识别的词序列                                                                         |
    +----------------------+----------------------------------------------------------------------------------------+
    | List<String> postags | 待识别的词的词性序列                                                                   |
    +----------------------+----------------------------------------------------------------------------------------+
    | List<String> ners    | | 命名实体识别结果，                                                                   |
    |                      | | 命名实体识别的结果为O时表示这个词不是命名实体，                                      |
    |                      | | 否则为{POS}-{TYPE}形式的标记，POS代表这个词在命名实体中的位置，TYPE表示命名实体类型  |
    +----------------------+----------------------------------------------------------------------------------------+


**示例程序**

.. code:: java

    import java.util.ArrayList;
    import java.util.List;
    import edu.hit.ir.ltp4j.*;

    public class TestNer {

     public static void main(String[] args) {
      if(NER.create("../../../ltp_data/ner.model")<0) {
       System.err.println("load failed");
        return;          
      }
       List<String> words = new ArrayList<String>();
       List<String> tags = new ArrayList<String>();
       List<String> ners = new ArrayList<String>();
       words.add("中国");tags.add("ns");
       words.add("国际");tags.add("n");
       words.add("广播");tags.add("n");
       words.add("电台");tags.add("n");
       words.add("创办");tags.add("v");
       words.add("于");tags.add("p");
       words.add("1941年");tags.add("m");
       words.add("12月");tags.add("m");
       words.add("3日");tags.add("m");
       words.add("。");tags.add("wp");

       NER.recognize(words, tags, ners);

      for (int i = 0; i < words.size(); i++) {
        System.out.println(ners.get(i));
       }

      NER.release();

     }
    }

依存句法分析接口
-----------------

.. java:type:: public class Parser

依存句法分析主要提供三个接口：

.. java:method:: public final native int create(String modelPath)

    功能：

    读取模型文件，初始化依存句法分析器

    参数：

    +---------------------------------------+--------------------------------------------------------------------+
    | 参数名                                | 参数描述                                                           |
    +=======================================+====================================================================+
    | String modelPath                      | 依存句法分析模型路径                                               |
    +---------------------------------------+--------------------------------------------------------------------+

.. java:method:: public final native void release()

    功能：

    释放模型文件，销毁依存句法分析器。

.. java:method:: public final native int parse(List<String> words, List<String> tags, List<Integer> heads, List<String> deprels)

    功能：

    调用依存句法分析接口

    参数：

    +----------------------+--------------------------------------------------------------------+
    | 参数名               | 参数描述                                                           |
    +======================+====================================================================+
    | List<String> words   | 待分析的词序列                                                     |
    +----------------------+--------------------------------------------------------------------+
    | List<String> tags    | 待分析的词的词性序列                                               |
    +----------------------+--------------------------------------------------------------------+
    | List<Integer> heads  | 结果依存弧，heads[i]代表第i个词的父亲节点的编号                    |
    +----------------------+--------------------------------------------------------------------+
    | List<String> deprels | 结果依存弧关系类型                                                 |
    +----------------------+--------------------------------------------------------------------+


**示例程序**

.. code:: java

	#include <iostream>
	#include <vector>
	#include "parser_dll.h"

	int main(int argc, char * argv[]) {
	    if (argc < 2) {
	        return -1;
	    }

	    void * engine = parser_create_parser(argv[1]);
	    if (!engine) {
	        return -1;
	    }

	    std::vector<std::string> words;
	    std::vector<std::string> postags;

	    words.push_back("一把手");      postags.push_back("n");
	    words.push_back("亲自");        postags.push_back("d");
	    words.push_back("过问");        postags.push_back("v");
	    words.push_back("。");          postags.push_back("wp");

	    std::vector<int>            heads;
	    std::vector<std::string>    deprels;

	    parser_parse(engine, words, postags, heads, deprels);

	    for (int i = 0; i < heads.size(); ++ i) {
	        std::cout << words[i] << "\t" << postags[i] << "\t"
	            << heads[i] << "\t" << deprels[i] << std::endl;
	    }

	    parser_release_parser(engine);
	    return 0;
	}


语义角色标注接口
-------------------

.. java:type:: public class SRL

语义角色标注主要提供三个接口：

.. java:method:: public final native int create(String modelPath)

    功能：

    读取模型文件，初始化语义角色标注器

    参数：

    +----------------------------+--------------------------------------------------------------------+
    | 参数名                     | 参数描述                                                           |
    +============================+====================================================================+
    | String modelPath           | 语义角色标注模型文件夹所在路径                                     |
    +----------------------------+--------------------------------------------------------------------+

.. java:method:: public final native void release()

    功能：

    释放模型文件，销毁命名实体识别器。


.. java:method:: public final native int srl(List<String> words, List<String> tags, List<String> ners, List<Integer> heads, List<String> deprels, List<Pair<Integer, List<Pair<String, Pair<Integer, Integer>>>>> srls)

    功能：

    调用命名实体识别接口

    参数：

    +---------------------------------------------------+-----------------------------------------------------------+
    | 参数名                                            | 参数描述                                                  |
    +===================================================+===========================================================+
    | List<String> words                                | 输入的词序列                                              |
    +---------------------------------------------------+-----------------------------------------------------------+
    | List<String> tags                                 | 输入的词性序列                                            |
    +---------------------------------------------------+-----------------------------------------------------------+
    | List<String> ners                                 | 输入的命名实体序列                                        |
    +---------------------------------------------------+-----------------------------------------------------------+
    | List<Integer> heads                               | 这个词的父节点的编号 [#f1]_                               |
    +---------------------------------------------------+-----------------------------------------------------------+
    | List<String> deprels                              | 这个词的父节点的依存关系类型                              |
    +---------------------------------------------------+-----------------------------------------------------------+
    | List<Pair<String, Pair<Integer, Integer>>>>> srls | 结果语义角色标注                                          |
    +---------------------------------------------------+-----------------------------------------------------------+

常见问题
--------

.. rubric:: 注

.. [#f1] 编号从0记起