package org.example.progetto_universitario;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import java.util.List;

import org.example.progetto_universitario.model.VoloAereo;
import org.example.progetto_universitario.service.GestoreVoli;

public class MainController {

    @FXML private AnchorPane pannelloRicerca;
    @FXML private AnchorPane pannelloPrenotazione;
    @FXML private AnchorPane pannelloSuccesso;

    @FXML private TextField txtPartenza;
    @FXML private TextField txtDestinazione;
    @FXML private TableView<VoloAereo> tabellaVoli;
    @FXML private TableColumn<VoloAereo, String> colCodice;
    @FXML private TableColumn<VoloAereo, String> colCompagnia;
    @FXML private TableColumn<VoloAereo, String> colPartenza;
    @FXML private TableColumn<VoloAereo, String> colDestinazione;
    @FXML private TableColumn<VoloAereo, String> colData;
    @FXML private TableColumn<VoloAereo, String> colOraP;
    @FXML private TableColumn<VoloAereo, String> colOraA;
    @FXML private TableColumn<VoloAereo, Integer> colPosti;
    @FXML private TableColumn<VoloAereo, Double> colPrezzo;
    @FXML private TextArea txtAreaLog;

    @FXML private Label lblVoloSelezionato;
    @FXML private TextField txtNome;
    @FXML private TextField txtCognome;
    @FXML private TextField txtCittadinanza;
    @FXML private ChoiceBox<String> choiceGenere;

    @FXML private Label lblMessaggioSuccesso;

    private GestoreVoli gestoreVoli;
    private VoloAereo voloScelto;

    @FXML
    public void initialize() {
        gestoreVoli = new GestoreVoli();

        pannelloRicerca.setVisible(true);
        pannelloPrenotazione.setVisible(false);
        pannelloSuccesso.setVisible(false);

        if (choiceGenere != null) {
            choiceGenere.setItems(FXCollections.observableArrayList("Maschile", "Femminile", "Altro"));
            choiceGenere.setValue("Maschile");
        }

        colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
        colCompagnia.setCellValueFactory(new PropertyValueFactory<>("compagnia"));
        colPartenza.setCellValueFactory(new PropertyValueFactory<>("partenza"));
        colDestinazione.setCellValueFactory(new PropertyValueFactory<>("destinazione"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataPartenza"));
        colOraP.setCellValueFactory(new PropertyValueFactory<>("oraPartenza"));
        colOraA.setCellValueFactory(new PropertyValueFactory<>("oraArrivo"));
        colPosti.setCellValueFactory(new PropertyValueFactory<>("postiDisponibili"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoFinale"));

        tabellaVoli.setItems(gestoreVoli.getCatalogoVoli());

        // Gestione dei log in tempo reale sulla TextArea
        gestoreVoli.setLogListener(messaggio -> Platform.runLater(() -> {
            if (txtAreaLog != null) {
                txtAreaLog.appendText(messaggio + "\n");
            }
            tabellaVoli.refresh();
        }));
    }

    @FXML
    private void gestisciCerca() {
        String part = txtPartenza.getText().trim();
        String dest = txtDestinazione.getText().trim();

        if (part.isEmpty() && dest.isEmpty()) {
            tabellaVoli.setItems(gestoreVoli.getCatalogoVoli());
            return;
        }
        List<VoloAereo> filtrati = gestoreVoli.cercaVoli(part, dest);
        tabellaVoli.setItems(FXCollections.observableArrayList(filtrati));
    }

    @FXML
    private void vaiAPrenotazione() {
        voloScelto = tabellaVoli.getSelectionModel().getSelectedItem();
        if (voloScelto == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Seleziona un volo per procedere.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        lblVoloSelezionato.setText("Stai prenotando il volo: " + voloScelto.getCodice() +
                " (" + voloScelto.getPartenza() + " -> " + voloScelto.getDestinazione() + ")");

        pannelloRicerca.setVisible(false);
        pannelloPrenotazione.setVisible(true);
    }

    @FXML
    private void gestisciPrenota() {
        String nome = txtNome.getText().trim();
        String cognome = txtCognome.getText().trim();
        String cittadinanza = txtCittadinanza.getText().trim();
        String genere = choiceGenere.getValue();

        if (nome.isEmpty() || cognome.isEmpty() || cittadinanza.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Compila tutti i campi richiesti!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            gestoreVoli.prenotaVolo(voloScelto, nome, cognome, genere, cittadinanza);

            String messaggio = "Prenotazione effettuata con successo per " + txtNome.getText() + " " + txtCognome.getText() +
                    "\nsul volo " + voloScelto.getCodice() +
                    " da " + voloScelto.getPartenza() + " a " + voloScelto.getDestinazione();

            lblMessaggioSuccesso.setText(messaggio);

            pannelloPrenotazione.setVisible(false);
            pannelloSuccesso.setVisible(true);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            tornaHome();
        }
    }

    // IL METODO MANCANTE RICHIESTO DALL'FXML!
    @FXML
    private void tornaHome() {
        pannelloPrenotazione.setVisible(false);
        pannelloSuccesso.setVisible(false);
        pannelloRicerca.setVisible(true);
    }

    // Mantengo anche questo per sicurezza se referenziato altrove
    @FXML
    private void tornaAllaRicerca() {
        tornaHome();
    }
}