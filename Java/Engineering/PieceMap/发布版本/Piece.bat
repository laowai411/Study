@echo "设置程序运行最大内存"
java -Xms1024m -Xmx1024m -XX:MaxNewSize=256m  -XX:MaxPermSize=256m -jar PieceMap_1.0.0.jar