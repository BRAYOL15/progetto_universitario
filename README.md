# Sistema di Prenotazione Voli (JavaFX & Multithreading)

Questo progetto è un'applicazione desktop sviluppata in *Java* con interfaccia grafica *JavaFX (Scene Builder)* per l'esame universitario di Programmazione Java. Il software simula un sistema di ricerca e prenotazione voli in tempo reale, implementando un'architettura rigorosamente orientata agli oggetti e conforme ai principi *SOLID*.

---

## 🛠️ Funzionalità Principali

* *Interfaccia Grafica Intuitiva:* Sviluppata in JavaFX con Scene Builder seguendo il pattern architetturale *MVC (Model-View-Controller)*.
* *Ricerca Avanzata:* Filtra i voli per partenza, destinazione e soglia di prezzo massimo istantaneamente.
* *Simulatore Prezzi Dinamici:* Un motore concorrente che simula le fluttuazioni del mercato reale in background.
* *Sistema di Prenotazione Sicuro:* Gestione dei posti disponibili con prevenzione dei conflitti di prenotazione.

---

## 📚 Moduli d'Esame Coperti e Implementazione

Il progetto è stato strutturato per dimostrare la padronanza di tutti i concetti chiave affrontati durante il semestre:

### 1. Architettura OOP & Principi SOLID 
* *Incapsulamento:* Tutti i modelli di dati (es. VoloAereo) proteggono il proprio stato tramite campi privati e metodi getter/setter dedicati.
* *Ereditarietà e Polimorfismo:* Creazione di una classe base astratta TrattaViaggio estesa dalla sottoclasse VoloAereo. Il calcolo del prezzo finale è polimorfico (getPrezzoFinale()), variando automaticamente se i posti sul volo scarseggiano.
* *SOLID:* Applicato il Single Responsibility Principle separando nettamente i dati del modello (model), la logica di business (service) e la gestione della GUI (controller).

### 2. Strutture Dati & Generics 
* *Generics:* Implementazione della classe Prenotazione<T extends TrattaViaggio>. L'uso dei Generics con bounded type garantisce la massima sicurezza dei tipi e la riutilizzabilità del codice (estendibile in futuro anche a tratte ferroviarie o hotel).
* *Data Structures:* Utilizzo di collezioni avanzate del Java Collections Framework per l'organizzazione ottimale del catalogo.

### 3. Gestione delle Eccezioni 
* Definizione dell'eccezione controllata personalizzata PostiEsauritiException. 
* Il sistema intercetta i casi limite (es. tentativo di prenotazione a posti terminati o errori di digitazione dell'utente) e risponde graficamente tramite finestre di Alert senza provocare il crash del software.

### 4. Programmazione Funzionale 
* La logica di ricerca e filtraggio dei voli all'interno della classe GestoreVoli sfrutta appieno le *Java Stream API* e le espressioni *Lambda*, evitando cicli iterativi complessi e migliorando la leggibilità del codice.

### 5. Multithreading e Concorrenza 
* *Thread in Background:* Un thread secondario (configurato come Daemon) simula il mercato aggiornando i prezzi dei voli ogni 8 secondi in totale autonomia.
* *Thread-Safety:* Per evitare race conditions tra il thread dei prezzi e l'azione di prenotazione dell'utente, l'accesso alle risorse condivise è regolato tramite blocchi sincronizzati (synchronized) e collezioni thread-safe (CopyOnWriteArrayList).
* *UI Thread:* Gli aggiornamenti grafici generati dal thread in background vengono iniettati in sicurezza nella GUI tramite Platform.runLater().

---

