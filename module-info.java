module org.example.progetto_universitario {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.progetto_universitario to javafx.fxml;
    // RIGA FONDAMENTALE: Apre il pacchetto del modello a JavaFX per permettergli di leggere i getter
    opens org.example.progetto_universitario.model to javafx.base, javafx.fxml;
    exports org.example.progetto_universitario;
}