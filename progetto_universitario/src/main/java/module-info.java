module org.example.progetto_universitario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.progetto_universitario to javafx.fxml;
    opens org.example.progetto_universitario.model to javafx.base, javafx.fxml;
    exports org.example.progetto_universitario;
}