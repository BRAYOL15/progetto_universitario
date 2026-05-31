package org.example.progetto_universitario.service;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.progetto_universitario.model.VoloAereo;
import org.example.progetto_universitario.model.Prenotazione;
import org.example.progetto_universitario.exception.PostiEsauritiException;
import java.util.ArrayList;
import java.util.List;

public class GestoreVoli {
    private ObservableList<VoloAereo> catalogoVoli = FXCollections.observableArrayList();
    private List<Prenotazione> elencoPrenotazioni = new ArrayList<>();
    private LogListener logListener;

    public interface LogListener {
        void onLog(String messaggio);
    }

    public GestoreVoli() {
        // Inserimento dei voli con: Codice, Compagnia, Partenza, Destinazione, Posti, Prezzo, Data, OraPartenza, OraArrivo
        catalogoVoli.add(new VoloAereo("AZ123", "ITA Airways", "Bologna", "Roma", 45, 105.61, "15/06/2026", "10:30", "11:45"));
        catalogoVoli.add(new VoloAereo("FR456", "Ryanair", "Bologna", "Roma", 1, 19.99, "16/06/2026", "21:00", "22:15"));
        catalogoVoli.add(new VoloAereo("LH789", "Lufthansa", "Milano", "Parigi", 150, 125.50, "20/06/2026", "07:45", "09:15"));

        faiPartireThreadPrezzi();
    }

    public ObservableList<VoloAereo> getCatalogoVoli() {
        return this.catalogoVoli;
    }

    public void setLogListener(LogListener listener) {
        this.logListener = listener;
    }

    public List<VoloAereo> cercaVoli(String partenza, String destinazione) {
        List<VoloAereo> ris = new ArrayList<>();
        for (VoloAereo v : catalogoVoli) {
            boolean pMatch = partenza.isEmpty() || v.getPartenza().toLowerCase().contains(partenza.toLowerCase());
            boolean dMatch = destinazione.isEmpty() || v.getDestinazione().toLowerCase().contains(destinazione.toLowerCase());
            if (pMatch && dMatch) {
                ris.add(v);
            }
        }
        return ris;
    }

    public void prenotaVolo(VoloAereo volo, String nome, String cognome, String genere, String cittadinanza) throws PostiEsauritiException {
        if (volo.getPostiDisponibili() <= 0) {
            throw new PostiEsauritiException("Posti esauriti sul volo: " + volo.getCodice());
        }
        volo.decresciPosti();
        elencoPrenotazioni.add(new Prenotazione(volo, nome, cognome, genere, cittadinanza));
    }

    private void faiPartireThreadPrezzi() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    for (VoloAereo v : catalogoVoli) {
                        double variazione = (Math.random() - 0.5) * 4;
                        double nuovoPrezzo = Math.round((v.getPrezzoFinale() + variazione) * 100.0) / 100.0;
                        if (nuovoPrezzo < 10) nuovoPrezzo = 10.0;

                        v.setPrezzoBase(nuovoPrezzo);

                        if (logListener != null) {
                            // Questo scrive il prezzo specifico del volo nel log!
                            String logSingolo = "[Thread-Prezzi] Aggiornato volo " + v.getCodice() +
                                    ". Nuovo prezzo base: " + nuovoPrezzo + "€";
                            logListener.onLog(logSingolo);
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
