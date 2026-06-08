package org.example.progetto_universitario;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.progetto_universitario.model.VoloAereo;
import org.example.progetto_universitario.service.GestoreVoli;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    // --- ELEMENTI FXML (Pannelli principali) ---
    @FXML private AnchorPane pannelloTratta;
    @FXML private AnchorPane pannelloParametri;
    @FXML private AnchorPane pannelloSoluzioni;
    @FXML private AnchorPane pannelloRiepilogo;
    @FXML private AnchorPane pannelloDatiPasseggeri;
    @FXML private AnchorPane pannelloSuccesso;

    // --- ELEMENTI SCHERMATA 1 & 2 (Campi di ricerca) ---
    @FXML private TextField txtPartenza;
    @FXML private TextField txtDestinazione;
    @FXML private DatePicker dateAndata;
    @FXML private ComboBox<String> comboOraAndata;
    @FXML private DatePicker dateRitorno;
    @FXML private ComboBox<String> comboOraRitorno;
    @FXML private ChoiceBox<String> choiceTipoPasseggero;

    // --- ELEMENTI SCHERMATA 3 & 4 (Soluzioni e Riepilogo) ---
    @FXML private Label lblRiepilogoTratta;
    @FXML private VBox vboxRisultati;
    @FXML private Label lblDettaglioVoloCodice;
    @FXML private Label lblDettaglioVoloTratta;
    @FXML private Label lblDettaglioVoloOrario;
    @FXML private Label lblQuantitaBiglietti;
    @FXML private Label lblPrezzoTotaleRiepilogo;

    // --- ELEMENTI SCHERMATA 5 & SUCCESSO (Passeggeri e Ricevuta) ---
    @FXML private VBox vboxFormPasseggeri;
    @FXML private Label lblMessaggioSuccesso;

    // --- VARIABILI DI LOGICA INTERNA ---
    private GestoreVoli gestoreVoli;
    private VoloAereo voloScelto;
    private int quantitaBiglietti = 1;

    // Liste dinamiche per raccogliere e validare i dati di tutti i passeggeri
    private List<TextField> listaCampiNomi = new ArrayList<>();
    private List<TextField> listaCampiCognomi = new ArrayList<>();
    private List<DatePicker> listaCampiDateNascita = new ArrayList<>();
    private List<TextField> listaCampiTelefoni = new ArrayList<>();
    private List<TextField> listaCampiEmail = new ArrayList<>();

    @FXML
    public void initialize() {
        // Inizializza il catalogo con i 100 voli
        this.gestoreVoli = new GestoreVoli();

        // Configurazione iniziale delle ComboBox e ChoiceBox simulando orari e opzioni
        if (comboOraAndata != null) comboOraAndata.getItems().addAll("08:00", "12:00", "16:00", "20:00");
        if (comboOraRitorno != null) comboOraRitorno.getItems().addAll("08:00", "12:00", "16:00", "20:00");
        if (choiceTipoPasseggero != null) {
            choiceTipoPasseggero.getItems().addAll("Adulto", "Bambino (2-11 anni)", "Neonato (0-23 mesi)");
            choiceTipoPasseggero.setValue("Adulto");
        }
    }

    // --- GESTIONE NAVIGAZIONE SCHERMATE ---

    @FXML
    private void vaiAParametriRicerca() {
        if (txtPartenza.getText().trim().isEmpty() || txtDestinazione.getText().trim().isEmpty()) {
            mostraAllerta("Campi vuoti", "Inserisci sia la città di partenza che quella di arrivo!");
            return;
        }
        pannelloTratta.setVisible(false);
        pannelloParametri.setVisible(true);
    }

    @FXML
    private void tornaATratta() {
        pannelloParametri.setVisible(false);
        pannelloTratta.setVisible(true);
    }

    @FXML
    private void vaiASoluzioniVoli() {
        gestisciCerca();
        pannelloParametri.setVisible(false);
        pannelloSoluzioni.setVisible(true);
    }

    @FXML
    private void tornaAParametri() {
        pannelloSoluzioni.setVisible(false);
        pannelloParametri.setVisible(true);
    }

    @FXML
    private void tornaASoluzioni() {
        pannelloRiepilogo.setVisible(false);
        pannelloSoluzioni.setVisible(true);
    }

    @FXML
    private void tornaARiepilogo() {
        pannelloDatiPasseggeri.setVisible(false);
        pannelloRiepilogo.setVisible(true);
    }

    // --- LOGICA DI RICERCA VOLI (Schermata 3) ---

    private void gestisciCerca() {
        String part = txtPartenza.getText().trim();
        String dest = txtDestinazione.getText().trim();

        lblRiepilogoTratta.setText(part + " → " + dest);

        // Se i campi sono vuoti, mostra l'intero catalogo per evitare blocchi (Risolto bug riga 75)
        if (part.isEmpty() && dest.isEmpty()) {
            popolaVBoxConVoli(gestoreVoli.getCatalogoVoli());
            return;
        }

        List<VoloAereo> filtrati = gestoreVoli.cercaVoli(part, dest);
        popolaVBoxConVoli(filtrati);
    }

    private void popolaVBoxConVoli(List<VoloAereo> voli) {
        vboxRisultati.getChildren().clear();

        if (voli.isEmpty()) {
            Label lblNo = new Label("Nessun volo trovato per questa tratta nel mese di Giugno.");
            lblNo.setStyle("-fx-font-style: italic; -fx-text-fill: #777777;");
            vboxRisultati.getChildren().add(lblNo);
            return;
        }

        for (VoloAereo v : voli) {
            HBox card = new HBox(20);
            card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8;");
            card.setPadding(new Insets(15));
            card.setPrefWidth(840);

            VBox info = new VBox(5);
            Label lblCompagnia = new Label(v.getCompagnia() + " - Volo " + v.getCodice());
            lblCompagnia.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #1a3c6c;");
            Label lblDettagli = new Label("Orario: " + v.getOraPartenza() + " -> " + v.getOraArrivo() + " | Data: " + v.getDataPartenza() + " | Posti: " + v.getPostiDisponibili());
            info.getChildren().addAll(lblCompagnia, lblDettagli);

            Label lblPrezzo = new Label(String.format("%.2f €", v.getPrezzoFinale()));
            lblPrezzo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #dc2626;");

            Button btnSeleziona = new Button("Seleziona");
            btnSeleziona.setStyle("-fx-background-color: #1a3c6c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
            btnSeleziona.setOnAction(e -> mostraRiepilogoOfferta(v));

            HBox bottoniEPrezzo = new HBox(15, lblPrezzo, btnSeleziona);
            bottoniEPrezzo.setPadding(new Insets(0, 0, 0, 100));

            card.getChildren().addAll(info, bottoniEPrezzo);
            vboxRisultati.getChildren().add(card);
        }
    }

    // --- LOGICA DI CONTROLLO QUANTITÀ BIGLIETTI (Schermata 4) ---

    private void mostraRiepilogoOfferta(VoloAereo v) {
        this.voloScelto = v;
        this.quantitaBiglietti = 1;

        lblDettaglioVoloCodice.setText("Volo: " + v.getCodice() + " (" + v.getCompagnia() + ")");
        lblDettaglioVoloTratta.setText("Tratta: " + v.getPartenza() + " → " + v.getDestinazione());
        lblDettaglioVoloOrario.setText("Orario: " + v.getOraPartenza() + " del " + v.getDataPartenza());

        lblQuantitaBiglietti.setText(String.valueOf(quantitaBiglietti));
        lblPrezzoTotaleRiepilogo.setText(String.format("%.2f €", v.getPrezzoFinale()));

        pannelloSoluzioni.setVisible(false);
        pannelloRiepilogo.setVisible(true);
    }

    @FXML
    private void aggiungiBiglietti() {
        if (voloScelto != null && quantitaBiglietti < voloScelto.getPostiDisponibili()) {
            quantitaBiglietti++;
            aggiornaPrezzoTotaleRiepilogo();
        }
    }

    @FXML
    private void diminuisciBiglietti() {
        if (quantitaBiglietti > 1) {
            quantitaBiglietti--;
            aggiornaPrezzoTotaleRiepilogo();
        }
    }

    private void aggiornaPrezzoTotaleRiepilogo() {
        lblQuantitaBiglietti.setText(String.valueOf(quantitaBiglietti));
        double totale = voloScelto.getPrezzoFinale() * quantitaBiglietti;
        lblPrezzoTotaleRiepilogo.setText(String.format("%.2f €", totale));
    }

    // --- COMPILAZIONE MODULI ED EXTRA PASSEGGERI (Schermata 5) ---

    @FXML
    private void vaiADatiPasseggeri() {
        vboxFormPasseggeri.getChildren().clear();
        listaCampiNomi.clear();
        listaCampiCognomi.clear();
        listaCampiDateNascita.clear();
        listaCampiTelefoni.clear();
        listaCampiEmail.clear();

        for (int i = 0; i < quantitaBiglietti; i++) {
            VBox cardPassegero = new VBox(10);
            cardPassegero.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8;");
            cardPassegero.setPadding(new Insets(15));

            Label lblTitoloPasseggero = new Label("Dati Passeggero " + (i + 1));
            lblTitoloPasseggero.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a3c6c; -fx-font-size: 14px;");

            TextField txtNome = new TextField();
            txtNome.setPromptText("Nome *");
            txtNome.setPrefHeight(38);

            TextField txtCognome = new TextField();
            txtCognome.setPromptText("Cognome *");
            txtCognome.setPrefHeight(38);

            DatePicker dateNascita = new DatePicker();
            dateNascita.setPromptText("Data di nascita (gg/mm/aaaa) *");
            dateNascita.setPrefHeight(38);
            dateNascita.setMaxWidth(Double.MAX_VALUE);

            TextField txtTelefono = new TextField();
            txtTelefono.setPromptText("Contatto telefonico *");
            txtTelefono.setPrefHeight(38);

            TextField txtEmail = new TextField();
            txtEmail.setPromptText("Email *");
            txtEmail.setPrefHeight(38);

            // Mappiamo i riferimenti interni
            listaCampiNomi.add(txtNome);
            listaCampiCognomi.add(txtCognome);
            listaCampiDateNascita.add(dateNascita);
            listaCampiTelefoni.add(txtTelefono);
            listaCampiEmail.add(txtEmail);

            cardPassegero.getChildren().addAll(lblTitoloPasseggero, txtNome, txtCognome, dateNascita, txtTelefono, txtEmail);
            vboxFormPasseggeri.getChildren().add(cardPassegero);
        }

        pannelloRiepilogo.setVisible(false);
        pannelloDatiPasseggeri.setVisible(true);
    }

    // --- CONFERMA DI PRENOTAZIONE E RICEVUTA EMAIL ---

    @FXML
    private void completaAcquistoFinale() {
        // Validazione: controlla che nessun campo inserito sia vuoto
        for (int i = 0; i < quantitaBiglietti; i++) {
            String nome = listaCampiNomi.get(i).getText().trim();
            String cognome = listaCampiCognomi.get(i).getText().trim();
            java.time.LocalDate dataNascita = listaCampiDateNascita.get(i).getValue();
            String telefono = listaCampiTelefoni.get(i).getText().trim();
            String email = listaCampiEmail.get(i).getText().trim();

            if (nome.isEmpty() || cognome.isEmpty() || dataNascita == null || telefono.isEmpty() || email.isEmpty()) {
                mostraAllerta("Campi Incompleti", "Compila tutti i dati del Passeggero " + (i + 1) + " prima di procedere!");
                return;
            }
        }

        // Estrae l'indirizzo email del richiedente principale (Passeggero 1)
        String emailAcquirente = listaCampiEmail.get(0).getText().trim();

        try {
            // Registra nel database simulato scalandone i posti
            for (int i = 0; i < quantitaBiglietti; i++) {
                String n = listaCampiNomi.get(i).getText().trim();
                String c = listaCampiCognomi.get(i).getText().trim();
                gestoreVoli.prenotaVolo(voloScelto, n, c, "Non Specificato", "Italiana"); // Evita unreported exception bloccante
            }

            // Testo di cortesia richiesto per la schermata finale
            String messaggioSuccesso = "Prenotazione effettuata con successo!\n\n" +
                    "Acquistati " + quantitaBiglietti + " biglietti per il volo " + voloScelto.getCodice() + " (" + voloScelto.getCompagnia() + ")\n" +
                    "Tratta: " + voloScelto.getPartenza() + " → " + voloScelto.getDestinazione() + "\n\n" +
                    "Le è stato mandato il riepilogo all'indirizzo email:\n👉 " + emailAcquirente + " 👈\n" +
                    "e le istruzioni su come procedere per acquistare definitivamente il biglietto.\n\n" +
                    "Grazie e arrivederci!";

            lblMessaggioSuccesso.setText(messaggioSuccesso);

            pannelloDatiPasseggeri.setVisible(false);
            pannelloSuccesso.setVisible(true);

        } catch (Exception e) {
            mostraAllerta("Posti esauriti", "Spiacenti, non ci sono abbastanza posti liberi.");
        }
    }

    @FXML
    private void resetInizialeHome() {
        txtPartenza.clear();
        txtDestinazione.clear();
        if (dateAndata != null) dateAndata.setValue(null);
        if (dateRitorno != null) dateRitorno.setValue(null);

        pannelloSuccesso.setVisible(false);
        pannelloTratta.setVisible(true);
    }

    private void mostraAllerta(String titolo, String testo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(testo);
        alert.showAndWait();
    }
}