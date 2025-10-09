# Arkanoid_OOP
The game for learning OOP   
JDK: https://www.azul.com/downloads/?package=jdk#downloads-table-zulu (version 25)
Cách chạy trên Mac:
Biên Dịch : 
javac --module-path /Users/nguyenminhnhat/Downloads/javafx-sdk-25/lib \
--add-modules javafx.controls,javafx.fxml,javafx.graphics \
-d out $(find src/main/java -name "*.java")
Run :
java --module-path /Users/nguyenminhnhat/Downloads/javafx-sdk-25/lib \
--add-modules javafx.controls,javafx.fxml,javafx.graphics \
-cp out uet.arkanoid.App
