@echo ���ṩ��jdk��װĿ¼Ϊ��%1
@echo off
IF EXIST %1\bin\java.exe (
   rem ��������ȷ�� Java2SDK ��װĿ¼����ʼ���û�������
   @setx JAVA_HOME %1
   @setx path %JAVA_HOME%\bin;%path%
   @setx classpath %classpath%;.
   @setx classpath %classpath%;%JAVA_HOME%\lib\tools.jar
   @setx classpath %classpath%;%JAVA_HOME%\lib\dt.jar
   @setx classpath %classpath%;%JAVA_HOME%\jre\lib\rt.jar
   @echo on
   @echo Java 2 SDK ��������������ϣ������˳���
   Piece.bat
)  ELSE (
      IF %1=="" (
      rem ��û���ṩ��װĿ¼����ʾ֮���˳�
      @echo on
      @echo û���ṩ Java2SDK �İ�װĿ¼,�����κ����ã������˳������������á�
      ) ELSE (
        rem ����ṩ�ǿյİ�װĿ¼��û��bin\java.exe����ָ����Ŀ¼Ϊ�����Ŀ¼
        @echo on
        @echo �Ƿ��� Java2SDK �İ�װĿ¼,�����κ����ã������˳������������á�
      )
)
