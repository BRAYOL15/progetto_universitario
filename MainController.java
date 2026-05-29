package org.example.progetto_universitario;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Platform;
import java.util.List;

import org.example.progetto_universitario.model.VoloAereo;
import org.example.progetto_universitario.service.GestoreVoli;
import org.example.progetto_universitario.exception.PostiEsauritiException;

public class MainController {

    @FXML private TextField txtPartenza;
    @FXML private TextField txtDestinazione;
    @FXML private TextField txtNomePasseggero;
    @FXML private TextArea txtAreaLog;

    @FXML private TableView<VoloAereo> tabellaVoli;
    @FXML private TableColumn<VoloAereo, String> colCodice;
    @FXML private TableColumn<VoloAereo, String> colCompagnia;
    @FXML private TableColumn<VoloAereo, String> colPartenza;
    @FXML private TableColumn<VoloAereo, String> colDestinazione;
    @FXML private TableColumn<VoloAereo, Integer> colPosti;
    @FXML private TableColumn<VoloAereo, Double> colPrezzo;

    private GestoreVoli gestoreVoli;

    @FXML
    public void initialize() {
        gestoreVoli = new GestoreVoli();

        // Configurazione rigorosa delle colonne legandole ai nomi dei metodi di VoloAereo
        colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
        colCompagnia.setCellValueFactory(new PropertyValueFactory<>("compagnia"));
        colPartenza.setCellValueFactory(new PropertyValueFactory<>("partenza"));
        colDestinazione.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
        colPosti.setCellValueFactory(new PropertyValueFactory<>("postiDisponibili"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoFinale"));

        // Associa la lista nativa del gestore per evitare tabelle vuote all'avvio
        if (gestoreVoli.getCatalogoVoli() != null) {
            tabellaVoli.setItems(gestoreVoli.getCatalogoVoli());
        }

        // Cattura l'output del thread e aggiorna la UI in sicurezza
        gestoreVoli.setLogListener(messaggio -> {
            Platform.runLater(() -> {
                txtAreaLog.appendText(messaggio + "\n");
                tabellaVoli.refresh(); // Ridispone a video i prezzi aggiornati
            });
        });
    }

    @FXML
    private void gestisciCerca() {
        String partenza = txtPartenza.getText().trim();
        String destinazione = txtDestinazione.getText().trim();

        if (partenza.isEmpty() && destinazione.isEmpty()) {
            tabellaVoli.setItems(gestoreVoli.getCatalogoVoli());
            return;
        }

        List<VoloAereo> risultatiFiltrati = gestoreVoli.cercaVoli(partenza, destinazione);
        if (risultatiFiltrati != null) {
            tabellaVoli.setItems(FXCollections.observableArrayList(risultatiFiltrati));
        }
    }

    @FXML
    private void gestisciPrenota() {
        VoloAereo voloSelezionato = tabellaVoli.getSelectionModel().getSelectedItem();
        if (voloSelezionato == null) {
            mostraAlertInformativo("Nessuna Selezione", "Seleziona un volo dalla tabella per poterlo prenotare.");
            return;
        }

        String nomePasseggero = txtNomePasseggero.getText().trim();
        if (nomePasseggero.isEmpty()) {
            mostraAlertInformativo("Manca il Nome", "Inserisci il nome del passeggero prima di prenotare.");
            return;
        }

        try {
            gestoreVoli.prenotaVolo(voloSelezionato, nomePasseggero);

            mostraAlertInformativo("Prenotazione Confermata",
                    "Prenotazione effettuata con successo per " + nomePasseggero + " sul volo " + voloSelezionato.getCodice());

            tabellaVoli.refresh();
            txtNomePasseggero.clear();

        } catch (PostiEsauritiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Posti Esauriti");
            alert.setHeaderText("Impossibile procedere");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void mostraAlertInformativo(String titolo, String contenuto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(contenuto);
        alert.showAndWait();
    }
}