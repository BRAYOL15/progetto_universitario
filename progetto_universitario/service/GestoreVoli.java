package org.example.progetto_universitario.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.progetto_universitario.model.VoloAereo;
import java.util.ArrayList;
import java.util.List;

public class GestoreVoli {

    private final ObservableList<VoloAereo> catalogoVoli;
    private LogListener logListener;

    public interface LogListener {
        void onLog(String messaggio);
    }

    public void setLogListener(LogListener logListener) {
        this.logListener = logListener;
    }

    public GestoreVoli() {
        this.catalogoVoli = FXCollections.observableArrayList();
        inizializzaCatalogo();
        faiPartireThreadPrezzi();
    }

    private void inizializzaCatalogo() {
        // 23 VOLI - 8 COMPAGNIE DIFFERENTI (ITA Airways, Ryanair, Lufthansa, Emirates, Delta Air Lines, Qatar Airways, Air France, Royal Air Maroc)

        // Tratte Nazionali (Italia)
        catalogoVoli.add(new VoloAereo("AZ101", "ITA Airways", "Bologna", "Roma", "15/06/2026", "10:30", "11:45", 45, 80.0));
        catalogoVoli.add(new VoloAereo("FR456", "Ryanair", "Bologna", "Roma", "16/06/2026", "21:00", "22:15", 1, 15.0));
        catalogoVoli.add(new VoloAereo("AZ102", "ITA Airways", "Milano", "Napoli", "18/06/2026", "08:00", "09:15", 85, 95.0));
        catalogoVoli.add(new VoloAereo("FR202", "Ryanair", "Pisa", "Palermo", "19/06/2026", "14:20", "15:55", 30, 29.99));
        catalogoVoli.add(new VoloAereo("AZ103", "ITA Airways", "Roma", "Venezia", "20/06/2026", "17:45", "18:50", 50, 75.0));

        // Europa
        catalogoVoli.add(new VoloAereo("LH789", "Lufthansa", "Milano", "Parigi", "20/06/2026", "07:45", "09:15", 150, 120.0));
        catalogoVoli.add(new VoloAereo("AF432", "Air France", "Roma", "Parigi", "21/06/2026", "11:20", "13:30", 62, 145.0));
        catalogoVoli.add(new VoloAereo("LH890", "Lufthansa", "Bologna", "Monaco", "22/06/2026", "06:30", "07:40", 90, 110.0));
        catalogoVoli.add(new VoloAereo("FR901", "Ryanair", "Milano", "Londra", "23/06/2026", "11:15", "12:30", 12, 34.99));
        catalogoVoli.add(new VoloAereo("AF112", "Air France", "Firenze", "Amsterdam", "24/06/2026", "15:10", "17:25", 40, 160.0));
        catalogoVoli.add(new VoloAereo("FR555", "Ryanair", "Roma", "Madrid", "25/06/2026", "09:00", "11:30", 8, 49.99));

        // America (Nord e Sud)
        catalogoVoli.add(new VoloAereo("DL501", "Delta Air Lines", "Roma", "New York", "26/06/2026", "10:15", "14:20", 210, 550.0));
        catalogoVoli.add(new VoloAereo("DL502", "Delta Air Lines", "Milano", "Atlanta", "27/06/2026", "11:30", "16:00", 180, 620.0));
        catalogoVoli.add(new VoloAereo("AZ301", "ITA Airways", "Roma", "Miami", "28/06/2026", "08:40", "13:55", 140, 590.0));
        catalogoVoli.add(new VoloAereo("AF099", "Air France", "Parigi", "Los Angeles", "29/06/2026", "13:15", "16:45", 250, 710.0));
        catalogoVoli.add(new VoloAereo("DL888", "Delta Air Lines", "Roma", "San Paolo", "30/06/2026", "22:00", "06:30", 195, 780.0));

        // Africa
        catalogoVoli.add(new VoloAereo("AT201", "Royal Air Maroc", "Bologna", "Casablanca", "15/06/2026", "18:20", "20:40", 70, 210.0));
        catalogoVoli.add(new VoloAereo("AT202", "Royal Air Maroc", "Milano", "Marrakech", "16/06/2026", "14:05", "16:35", 55, 240.0));
        catalogoVoli.add(new VoloAereo("MS777", "Qatar Airways", "Roma", "Il Cairo", "17/06/2026", "13:00", "16:15", 110, 320.0));
        catalogoVoli.add(new VoloAereo("EK401", "Emirates", "Milano", "Il Cairo", "18/06/2026", "21:45", "01:00", 130, 350.0));
        catalogoVoli.add(new VoloAereo("AT203", "Royal Air Maroc", "Roma", "Tunisi", "19/06/2026", "09:15", "10:45", 48, 180.0));

        // Hub Internazionali / Medio Oriente
        catalogoVoli.add(new VoloAereo("EK205", "Emirates", "Milano", "Dubai", "20/06/2026", "14:05", "22:10", 310, 480.0));
        catalogoVoli.add(new VoloAereo("QR115", "Qatar Airways", "Roma", "Doha", "21/06/2026", "16:30", "23:15", 280, 510.0));
    }

    public ObservableList<VoloAereo> getCatalogoVoli() {
        return catalogoVoli;
    }

    public List<VoloAereo> cercaVoli(String partenza, String destinazione) {
        List<VoloAereo> risultati = new ArrayList<>();
        for (VoloAereo v : catalogoVoli) {
            boolean checkPartenza = partenza.isEmpty() || v.getPartenza().equalsIgnoreCase(partenza);
            boolean checkDestinazione = destinazione.isEmpty() || v.getDestinazione().equalsIgnoreCase(destinazione);
            if (checkPartenza && checkDestinazione) {
                risultati.add(v);
            }
        }
        return risultati;
    }

    public void prenotaVolo(VoloAereo volo, String nome, String cognome, String genere, String cittadinanza) throws Exception {
        if (volo.getPostiDisponibili() <= 0) {
            throw new Exception("Errore: I posti per questo volo sono esauriti!");
        }
        volo.setPostiDisponibili(volo.getPostiDisponibili() - 1);
    }

    private void faiPartireThreadPrezzi() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    for (VoloAereo v : catalogoVoli) {
                        double variazione = (Math.random() - 0.5) * 6;
                        double nuovoPrezzo = Math.round((v.getPrezzoFinale() + variazione) * 100.0) / 100.0;
                        if (nuovoPrezzo < 9.99) nuovoPrezzo = 9.99;

                        v.setPrezzoBase(nuovoPrezzo);

                        if (logListener != null) {
                            String logSingolo = "[Thread-Prezzi] Aggiornato volo " + v.getCodice() +
                                    " (" + v.getPartenza() + "->" + v.getDestinazione() +
                                    "). Nuovo prezzo base: " + nuovoPrezzo + "€";
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