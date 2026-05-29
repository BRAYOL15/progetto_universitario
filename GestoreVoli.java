package org.example.progetto_universitario.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.example.progetto_universitario.model.VoloAereo;
import org.example.progetto_universitario.model.Prenotazione;
import org.example.progetto_universitario.exception.PostiEsauritiException;

public class GestoreVoli {

    // Lista osservabile nativa per sincronizzare in automatico thread e tabella JavaFX
    private final ObservableList<VoloAereo> catalogoVoli = FXCollections.observableArrayList();
    private final List<Prenotazione> elencoPrenotazioni = new CopyOnWriteArrayList<>();
    private Consumer<String> logListener;

    public GestoreVoli() {
        // Voli di test caricati all'avvio
        catalogoVoli.add(new VoloAereo("AZ123", "ITA Airways", "Bologna", "Roma", 45, 59.99));
        catalogoVoli.add(new VoloAereo("FR456", "Ryanair", "Bologna", "Roma", 1, 19.99));
        catalogoVoli.add(new VoloAereo("LH789", "Lufthansa", "Milano", "Parigi", 150, 125.50));

        // Avvio del thread concorrente in background (Modulo E8)
        faiPartireThreadPrezzi();
    }

    public ObservableList<VoloAereo> getCatalogoVoli() {
        return catalogoVoli;
    }

    public void setLogListener(Consumer<String> listener) {
        this.logListener = listener;
    }

    // Ricerca tramite Stream API senza parametri superflui (Modulo E7)
    public List<VoloAereo> cercaVoli(String partenza, String destinazione) {
        return catalogoVoli.stream()
                .filter(v -> v.getPartenza().equalsIgnoreCase(partenza) &&
                        v.getDestinazione().equalsIgnoreCase(destinazione))
                .toList();
    }

    // Prenotazione con gestione delle eccezioni di controllo (Modulo E5)
    public void prenotaVolo(VoloAereo volo, String passeggero) throws PostiEsauritiException {
        if (volo.getPostiDisponibili() <= 0) {
            throw new PostiEsauritiException("Posti esauriti sul volo selezionato: " + volo.getCodice());
        }
        volo.decresciPosti();
        elencoPrenotazioni.add(new Prenotazione(volo, passeggero));
    }

    // Thread in background per variazioni repentine dei prezzi di mercato (Modulo E8)
    private void faiPartireThreadPrezzi() {
        Thread threadPrezzi = new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(3500); // Cambio tariffa ogni 3.5 secondi

                    if (!catalogoVoli.isEmpty()) {
                        int index = random.nextInt(catalogoVoli.size());
                        VoloAereo volo = catalogoVoli.get(index);

                        double nuovoPrezzo = 15 + (110 * random.nextDouble());
                        volo.setPrezzoBase(Math.round(nuovoPrezzo * 100.0) / 100.0);

                        if (logListener != null) {
                            logListener.accept("[Thread-Prezzi] Aggiornato volo " + volo.getCodice() + ". Nuovo prezzo base: " + volo.getPrezzoFinale() + "€");
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadPrezzi.setDaemon(true);
        threadPrezzi.start();
    }
}