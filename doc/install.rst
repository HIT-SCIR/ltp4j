.. _install-label:

编译ltp4j
=========

如果您需要使用ltp4j，必须拥有两部分内容

* ltp4j.jar与C++代理程序
* LTP模型文件

其中，LTP模型文件可以从 `百度云 <http://pan.baidu.com/share/link?shareid=1988562907&uk=2738088569>`_，当前ltp4j对应的模型版本为3.3.1。本文档将着重介绍如何编译ltp4j.jar与其C++代理程序。

安装Maven
---------

ltp4j使用 `apache maven <https://maven.apache.org/index.html>`_ 进行构建。在构建ltp4j之前，您首先需要安装maven。安装方法请参考： `安装apache maven <https://maven.apache.org/install.html>`_。

编译ltp4j
---------

在确保安装maven的前提下（即 `mvn -h` 具有输出结果），您可以按照如下方式构建ltp4j。

1. 在命令行下进入ltp4j所在文件夹
2. `git submodule init`
3. `git submodule update`
4. `mvn -Dmaven.test.skip=true`

如果您编译提示成功同时项目根目录下包含 `target/ltp4j-{version}.jar`，证明已经编译成功。

nar-maven-plugin
~~~~~~~~~~~~~~~~

本部分将介绍编译ltp4j的一些技术考虑，与编译ltp4j无关。对这部分不感兴趣的用户可以忽略这部分文档。

ltp4j的基本技术考虑是 **使用户使用最简单的技术手段编译使用ltp4j** 。所以我们选择了maven作为构建工具，希望可以通过一条指令完成编译过程。
如前文所述，ltp4j需要ltp4j.jar及其C++代理程序两部分。
为了在maven中既能够使用java编译器编译jar又能够使用C++编译器编译C++代理程序，我们经过调研，决定使用 `nar-maven-plugin <https://github.com/maven-nar/nar-maven-plugin>`_ 。这一maven插件使我们可以在不同的系统架构下编译C++的代码 (AOL)。
在使用过程中，我们发现了这一插件的一系列bug，并通过贡献代码的方式进行了解决。


编译结果
--------

nar-maven-plugin的编译结果随操作系统的不同而存在差异。其生成的ltp4j.jar以及代理文件可以从如下路径找到

* jar：`./target/ltp4j-{version}.jar`
* 代理程序：`./target/ltp4j-{version}-{AOL}-jni/`

其中，`vesion` 代表ltp4j的版本。`AOL` 代表 **体系结构-系统-链接器** 。
举例来讲，

* Windows 64位系统使用MSVC编译对应的AOL为：amd64-Windows-msvc
* Ubuntu 64位系统使用gnuc++编译对应的AOL为：amd64-Linux-gpp

编译结果示例
~~~~~~~~~~~~

**64位Linux g++**

.. code:: shell

    $ find target/ -type f -name "*.jar" -or -name "*.so"
    target/ltp4j-0.1.0-SNAPSHOT.jar
    target/nar/ltp4j-0.1.0-SNAPSHOT-amd64-Linux-gpp-jni/lib/amd64-Linux-gpp/jni/libltp4j-0.1.0-SNAPSHOT.so


**64位windows MSVC**

.. code:: shell

    $ find target/ -type f -name "*.jar" -or -name "*.dll"
    target/ltp4j-0.1.0-SNAPSHOT.jar
    target/nar/ltp4j-0.1.0-SNAPSHOT-amd64-Windows-msvc-jni/lib/amd64-Windows-msvc/jni/ltp4j-0.1.0-SNAPSHOT.dll


常见问题
--------

