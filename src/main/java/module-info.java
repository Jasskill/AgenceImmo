module com.example.agenceimmo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.agenceimmo to javafx.fxml;
    exports com.example.agenceimmo;
}