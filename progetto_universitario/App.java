package org.example.progetto_universitario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FORZIAMO IL CARICAMENTO DEL FILE FXML CORRETTO E AGGIORNATO
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("prenotazioni-overview-view.fxml"));

        // Impostiamo le dimensioni della finestra coerenti con il foglio FXML (980x650)
        Scene scene = new Scene(fxmlLoader.load(), 980, 650);

        stage.setTitle("Sistema Universitario Gestione Voli");
        stage.setScene(scene);
        stage.setResizable(false); // Impedisce il ridimensionamento manuale per non scompaginare la grafica
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}