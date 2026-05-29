package org.example.progetto_universitario.model;

// Rappresenta l'associazione tra un passeggero e il volo scelto
public class Prenotazione {
    private VoloAereo volo;
    private String nomePasseggero;

    public Prenotazione(VoloAereo volo, String nomePasseggero) {
        this.volo = volo;
        this.nomePasseggero = nomePasseggero;
    }

    public VoloAereo getVolo() { return volo; }
    public String getNomePasseggero() { return nomePasseggero; }
}