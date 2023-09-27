module com.example.agenceimmo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.agenceimmo to javafx.fxml;
    exports com.example.agenceimmo;
}