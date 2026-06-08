package org.example.progetto_universitario.model;

public class VoloAereo {

    private String codice;
    private String compagnia;
    private String partenza;
    private String destinazione;
    private String dataPartenza;
    private String oraPartenza;
    private String oraArrivo;
    private double prezzoFinale;
    private int postiDisponibili;

    // Costruttore vuoto (necessario per l'inizializzazione classica con i setter)
    public VoloAereo() {
    }

    // Costruttore completo (comodo se vuoi creare l'oggetto in una sola riga)
    public VoloAereo(String codice, String compagnia, String partenza, String destinazione,
                     String dataPartenza, String oraPartenza, String oraArrivo,
                     double prezzoFinale, int postiDisponibili) {
        this.codice = codice;
        this.compagnia = compagnia;
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
        this.prezzoFinale = prezzoFinale;
        this.postiDisponibili = postiDisponibili;
    }

    // --- GETTER E SETTER (Allineati perfettamente al generatore) ---

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCompagnia() {
        return compagnia;
    }

    public void setCompagnia(String compagnia) {
        this.compagnia = compagnia;
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(String dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public String getOraPartenza() {
        return oraPartenza;
    }

    public void setOraPartenza(String oraPartenza) {
        this.oraPartenza = oraPartenza;
    }

    public String getOraArrivo() {
        return oraArrivo;
    }

    public void setOraArrivo(String oraArrivo) {
        this.oraArrivo = oraArrivo;
    }

    public double getPrezzoFinale() {
        return prezzoFinale;
    }

    public void setPrezzoFinale(double prezzoFinale) {
        this.prezzoFinale = prezzoFinale;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    // Metodo di utility comodo per stampare i dettagli del volo nei log di debug
    @Override
    public String toString() {
        return "VoloAereo{" +
                "codice='" + codice + '\'' +
                ", compagnia='" + compagnia + '\'' +
                ", tratta=" + partenza + " -> " + destinazione +
                ", data='" + dataPartenza + '\'' +
                ", prezzo=" + prezzoFinale + " €" +
                '}';
    }
}