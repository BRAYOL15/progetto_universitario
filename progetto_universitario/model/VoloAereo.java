package org.example.progetto_universitario.model;

public class VoloAereo {
    private String codice;
    private String compagnia;
    private String partenza;
    private String destinazione;
    private String dataPartenza;
    private String oraPartenza;
    private String oraArrivo;
    private int postiDisponibili;
    private double prezzoBase;

    public VoloAereo(String codice, String compagnia, String partenza, String destinazione,
                     String dataPartenza, String oraPartenza, String oraArrivo, int postiDisponibili, double prezzoBase) {
        this.codice = codice;
        this.compagnia = compagnia;
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
        this.postiDisponibili = postiDisponibili;
        this.prezzoBase = prezzoBase;
    }

    // GETTER
    public String getCodice() { return codice; }
    public String getCompagnia() { return compagnia; }
    public String getPartenza() { return partenza; }
    public String getDestinazione() { return destinazione; }
    public String getDataPartenza() { return dataPartenza; }
    public String getOraPartenza() { return oraPartenza; }
    public String getOraArrivo() { return oraArrivo; }
    public int getPostiDisponibili() { return postiDisponibili; }
    public double getPrezzoFinale() { return prezzoBase; }

    // SETTER (Risolve l'errore in rosso nel Gestore)
    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public void setPrezzoBase(double prezzoBase) {
        this.prezzoBase = prezzoBase;
    }
}