简介与背景知识
==============

ltp4j是 `语言技术平台 (Language Technology Platform, LTP) <http://ltp.readthedocs.org/zh_CN/latest/>`_ 接口的一个Java封装。 本项目旨在使Java用户可以本地调用LTP。

在使用ltp4j之前，您需要简要了解

* 什么是语言技术平台，它能否帮助您解决问题
* 如何安装语言技术平台
* 语言技术平台提供哪些编程接口

如果您对这些问题不了解，请首先阅读我们提供的有关语言技术平台的 `文档 <http://ltp.readthedocs.org/zh_CN/latest/>`_ 。在本文档的后续中，我们假定您已经阅读并成功编译并使用语言技术平台。


ltj4j的基本实现思路是依靠JNI技术实现在Java中调用C/C++动态库。我们建议您使用几分钟了解 `Java调用C/C++动态库 <http://www.cnblogs.com/icejoywoo/archive/2012/02/22/2363709.html>`_ 的实践方式。

ltp4j整个项目由两部分组成，他们分别是：

* ltp4j.jar：Java接口程序。
* C++代理程序：ltp4j加载的ltp动态库。

