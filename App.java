package org.example.progetto_universitario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

// Classe principale di avvio dell'applicazione JavaFX
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("prenotazioni-overview-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema Universitario Gestione Voli");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}