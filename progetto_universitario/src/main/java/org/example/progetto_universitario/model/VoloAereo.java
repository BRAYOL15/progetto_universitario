package org.example.progetto_universitario.model;

// Sottoclasse che estende TrattaViaggio aggiungendo i dettagli specifici del volo (Modulo E1)
public class VoloAereo extends TrattaViaggio {
    private String codice;
    private String compagnia;
    private int postiDisponibili;
    private double prezzoBase;

    public VoloAereo(String codice, String compagnia, String partenza, String destinazione, int postiDisponibili, double prezzoBase) {
        super(partenza, destinazione);
        this.codice = codice;
        this.compagnia = compagnia;
        this.postiDisponibili = postiDisponibili;
        this.prezzoBase = prezzoBase;
    }

    // I seguenti metodi Getter servono a JavaFX per mappare le colonne della tabella
    public String getCodice() { return codice; }
    public String getCompagnia() { return compagnia; }
    public int getPostiDisponibili() { return postiDisponibili; }
    public double getPrezzoBase() { return prezzoBase; }

    // Restituisce il prezzo corrente gestito dal thread
    public double getPrezzoFinale() {
        return prezzoBase;
    }

    public void setPrezzoBase(double prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public void decresciPosti() {
        if (this.postiDisponibili > 0) {
            this.postiDisponibili--;
        }
    }
}