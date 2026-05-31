package org.example.progetto_universitario.model;

public class VoloAereo extends TrattaViaggio {
    private String codice;
    private String compagnia;
    private int postiDisponibili;
    private double prezzoBase;

    // Campi richiesti per date e orari del volo
    private String dataPartenza;
    private String oraPartenza;
    private String oraArrivo;

    public VoloAereo(String codice, String compagnia, String partenza, String destinazione,
                     int postiDisponibili, double prezzoBase,
                     String dataPartenza, String oraPartenza, String oraArrivo) {
        super(partenza, destinazione);
        this.codice = codice;
        this.compagnia = compagnia;
        this.postiDisponibili = postiDisponibili;
        this.prezzoBase = prezzoBase;
        this.dataPartenza = dataPartenza;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
    }

    public String getCodice() { return codice; }
    public String getCompagnia() { return compagnia; }
    public int getPostiDisponibili() { return postiDisponibili; }
    public double getPrezzoFinale() { return prezzoBase; }

    // Getter usati da JavaFX per popolare le colonne della tabella
    public String getDataPartenza() { return dataPartenza; }
    public String getOraPartenza() { return oraPartenza; }
    public String getOraArrivo() { return oraArrivo; }

    public void setPrezzoBase(double prezzoBase) { this.prezzoBase = prezzoBase; }
    public void decresciPosti() { if (this.postiDisponibili > 0) this.postiDisponibili--; }
}