@echo 你提供的jdk安装目录为：%1
@echo off
IF EXIST %1\bin\java.exe (
   rem 如输入正确的 Java2SDK 安装目录，开始设置环境变量
   @setx JAVA_HOME %1
   @setx path %JAVA_HOME%\bin;%path%
   @setx classpath %classpath%;.
   @setx classpath %classpath%;%JAVA_HOME%\lib\tools.jar
   @setx classpath %classpath%;%JAVA_HOME%\lib\dt.jar
   @setx classpath %classpath%;%JAVA_HOME%\jre\lib\rt.jar
   @echo on
   @echo Java 2 SDK 环境参数设置完毕，正常退出。
   Piece.bat
)  ELSE (
      IF %1=="" (
      rem 如没有提供安装目录，提示之后退出
      @echo on
      @echo 没有提供 Java2SDK 的安装目录,不做任何设置，现在退出环境变量设置。
      ) ELSE (
        rem 如果提供非空的安装目录但没有bin\java.exe，则指定的目录为错误的目录
        @echo on
        @echo 非法的 Java2SDK 的安装目录,不做任何设置，现在退出环境变量设置。
      )
)
