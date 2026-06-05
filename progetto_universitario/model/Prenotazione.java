package org.example.progetto_universitario.model;

public class Prenotazione {
    private VoloAereo volo;
    private String nome;
    private String cognome;
    private String genere;
    private String cittadinanza;

    public Prenotazione(VoloAereo volo, String nome, String cognome, String genere, String cittadinanza) {
        this.volo = volo;
        this.nome = nome;
        this.cognome = cognome;
        this.genere = genere;
        this.cittadinanza = cittadinanza;
    }

    public VoloAereo getVolo() { return volo; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getGenere() { return genere; }
    public String getCittadinanza() { return cittadinanza; }
}