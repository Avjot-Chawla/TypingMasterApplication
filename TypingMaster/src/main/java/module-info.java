module com.example.typingmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.typingmaster to javafx.fxml;
    exports com.example.typingmaster;
}