package org.example.progetto_universitario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("prenotazioni-overview-view.fxml"));
        // Creiamo la scena con le dimensioni esatte del pannello blu
        Scene scene = new Scene(fxmlLoader.load(), 980, 650);
        stage.setTitle("Sistema Universitario Gestione Voli");
        stage.setScene(scene);
        stage.setResizable(false); // Impedisce il ridimensionamento per non scombinare la grafica
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}