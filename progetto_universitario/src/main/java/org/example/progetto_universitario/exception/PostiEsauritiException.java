package org.example.progetto_universitario.exception;

// Eccezione personalizzata per bloccare le prenotazioni su voli pieni (Modulo E5)
public class PostiEsauritiException extends Exception {
    public PostiEsauritiException(String messaggio) {
        super(messaggio);
    }
}