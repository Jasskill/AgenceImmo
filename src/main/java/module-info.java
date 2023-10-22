module com.example.agenceimmo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jsch;
    requires commons.vfs2;
    requires jBCrypt;
    requires org.apache.commons.net;


    opens com.example.agenceimmo to javafx.fxml;
    exports com.example.agenceimmo;
}