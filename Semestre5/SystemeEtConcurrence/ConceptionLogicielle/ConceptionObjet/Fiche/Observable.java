import java.util.Observable;
import java.util.Observer;

class MonObservable extends Observable {
    public void changerEtat() {
        setChanged();
        notifyObservers("État changé !");
    }
}

class MonObserver implements Observer {
    public void update(Observable o, Object arg) {
        System.out.println("Observer reçu : " + arg);
    }
}

// Usage
MonObservable obs = new MonObservable();
MonObserver monObs = new MonObserver();
obs.addObserver(monObs);
obs.changerEtat(); // Observer reçu : État changé !


//==============================================//

import java.util.*;

class MonObservable extends Observable {
    private String etat = "Initial";

    public void changerEtat(String nouveauEtat) {
        this.etat = nouveauEtat;
        setChanged();
        notifyObservers(nouveauEtat);
    }

    public String getEtat() { return etat; }
}

class MonObserver implements Observer {
    private String nom;

    public MonObserver(String nom) {
        this.nom = nom;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(nom + " : " + arg);
    }
}

// Usage
MonObservable obs = new MonObservable();
MonObserver obs1 = new MonObserver("Observateur 1");
MonObserver obs2 = new MonObserver("Observateur 2");

obs.addObserver(obs1);
obs.addObserver(obs2);

obs.changerEtat("Actif");
obs.changerEtat("Terminé");
