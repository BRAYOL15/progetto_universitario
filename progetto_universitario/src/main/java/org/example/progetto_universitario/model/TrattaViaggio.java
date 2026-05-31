package org.example.progetto_universitario.model;

// Classe base astratta che rappresenta una generica tratta (Modulo E1)
public abstract class TrattaViaggio {
    protected String partenza;
    protected String destinazione;

    public TrattaViaggio(String partenza, String destinazione) {
        this.partenza = partenza;
        this.destinazione = destinazione;
    }

    public String getPartenza() { return partenza; }
    public String getDestinazione() { return destinazione; }
}