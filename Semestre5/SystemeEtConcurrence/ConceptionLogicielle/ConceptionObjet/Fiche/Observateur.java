interface Observer {
    void miseAJour(String message);
}

class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void ajouterObserver(Observer o) {
        observers.add(o);
    }

    public void notifier(String message) {
        for (Observer o : observers) {
            o.miseAJour(message);
        }
    }

    public void changerEtat() {
        this.notifier("État changé !");
    }
}

class Observateur implements Observer {
    public void miseAJour(String message) {
        System.out.println("Observer : " + message);
    }
}

// Usage
Observable o = new Observable();
Observateur obs = new Observateur();
o.ajouterObserver(obs);
o.changerEtat(); // Observer : État changé !


//===========================================================================//

import java.util.*;

interface Observer {
    void miseAJour(String message);
}

class Observable {
    private List<Observer> observers = new ArrayList<>();

    public void ajouterObserver(Observer o) {
        observers.add(o);
    }

    public void notifier(String message) {
        for (Observer o : observers) {
            o.miseAJour(message);
        }
    }

    private String etat = "Initial";
    public void changerEtat(String nouveauEtat) {
        this.etat = nouveauEtat;
        this.notifier("État changé : " + nouveauEtat);
    }

    public String getEtat() { return etat; }
}

class Observateur implements Observer {
    private String nom;

    public Observateur(String nom) {
        this.nom = nom;
    }

    public void miseAJour(String message) {
        System.out.println(nom + " : " + message);
    }
}

// Usage
Observable o = new Observable();
Observateur obs1 = new Observateur("Observer 1");
Observateur obs2 = new Observateur("Observer 2");

o.ajouterObserver(obs1);
o.ajouterObserver(obs2);

o.changerEtat("Actif");
// Output :
// Observer 1 : État changé : Actif
// Observer 2 : État changé : Actif
