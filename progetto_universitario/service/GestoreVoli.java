package org.example.progetto_universitario.service;

import org.example.progetto_universitario.model.VoloAereo;
import java.util.ArrayList;
import java.util.List;

public class GestoreVoli {

    private List<VoloAereo> catalogoVoli;

    public GestoreVoli() {
        this.catalogoVoli = new ArrayList<>();
        inizializzaCentoVoli();
    }

    public List<VoloAereo> getCatalogoVoli() {
        return catalogoVoli;
    }

    public List<VoloAereo> cercaVoli(String partenza, String destinazione) {
        List<VoloAereo> filtrati = new ArrayList<>();
        for (VoloAereo v : catalogoVoli) {
            if (v.getPartenza().equalsIgnoreCase(partenza) && v.getDestinazione().equalsIgnoreCase(destinazione)) {
                filtrati.add(v);
            }
        }
        return filtrati;
    }

    public void prenotaVolo(VoloAereo volo, String nome, String cognome, String genere, String cittadinanza) throws Exception {
        if (volo.getPostiDisponibili() <= 0) {
            throw new Exception("Posti esauriti su questo volo!");
        }
        volo.setPostiDisponibili(volo.getPostiDisponibili() - 1);
        // Qui andrebbe la logica per salvare la prenotazione nel tuo sistema/DB
    }

    private void inizializzaCentoVoli() {
        // Array di supporto per generare dati realistici e variegati
        String[] itPartenze = {"Bologna", "Roma", "Milano", "Venezia"};

        String[] destinazioniMondiali = {
                "Parigi", "Madrid", "Londra", "Berlino", "Barcellona",
                "New York", "Tokyo", "Dubai", "Amsterdam", "Lisbona"
        };

        String[] compagnie = {"Ryanair", "ITA Airways", "Air France", "Lufthansa", "Emirates", "British Airways"};
        String[] orariPartenza = {"06:30", "08:15", "10:00", "13:45", "15:20", "17:00", "19:30", "22:10"};
        String[] orariArrivo = {"08:45", "10:30", "12:15", "16:00", "17:40", "19:15", "21:45", "00:25"};

        int contatore = 100;

        // Generazione ciclica di esattamente 100 voli per giugno 2026
        for (int i = 1; i <= 100; i++) {
            String part = itPartenze[i % itPartenze.length];
            String dest = destinazioniMondiali[i % destinazioniMondiali.length];

            // Evita che partenza e destinazione coincidano (caso limite matematico)
            if (part.equalsIgnoreCase(dest)) {
                dest = "Lisbona";
            }

            String comp = compagnie[i % compagnie.length];
            String oraP = orariPartenza[i % orariPartenza.length];
            String oraA = orariArrivo[i % orariArrivo.length];

            // Distribuisce i giorni dal 1 al 30 Giugno 2026
            int giorno = (i % 30) + 1;
            String dataStr = String.format("%02d/06/2026", giorno);

            // Codice volo univoco (es. AZ101, FR102...)
            String prefisso = comp.substring(0, 2).toUpperCase();
            String codiceVolo = prefisso + contatore;
            contatore++;

            // Calcolo di un prezzo dinamico basato sulla distanza simulata
            double prezzoBase = 45.00;
            if (dest.equals("New York") || dest.equals("Tokyo") || dest.equals("Dubai")) {
                prezzoBase = 420.00 + (i * 3); // Voli intercontinentali costano di più
            } else {
                prezzoBase = 19.99 + (i * 1.5); // Voli europei low/medium cost
            }

            int posti = 50 + (i % 100); // Posti disponibili tra 50 e 150

            // Crea l'oggetto usando il costruttore del tuo modello VoloAereo
            // Nota: Adatta l'ordine dei parametri in base al tuo costruttore specifico
            VoloAereo volo = new VoloAereo();
            volo.setCodice(codiceVolo);
            volo.setCompagnia(comp);
            volo.setPartenza(part);
            volo.setDestinazione(dest);
            volo.setDataPartenza(dataStr);
            volo.setOraPartenza(oraP);
            volo.setOraArrivo(oraA);
            volo.setPrezzoFinale(prezzoBase);
            volo.setPostiDisponibili(posti);

            // Aggiunge il volo generato al catalogo
            catalogoVoli.add(volo);
        }
    }
}