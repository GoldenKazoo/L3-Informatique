public class Etat {
    public void action() {
        System.out.println("État normal");
    }
}

public class EtatSpecial {
    public void action() {
        System.out.println("État spécial");
    }
}

public class Machine {
    private Etat etat;

    public Machine() {
        this.etat = new Etat();
    }

    public void changerEtat() {
        this.etat = new EtatSpecial();
    }

    public void faireAction() {
        this.etat.action();
    }
}

//======================================================================================//

enum Etat { 
    ARRÊTÉ, 
    EN_COURS, 
    TERMINÉ 
}

interface EtatMachine {
    void executer();
    void changerEtat();
}

class EtatArret implements EtatMachine {
    public void executer() { System.out.println("Machine arrêtée"); }
    public void changerEtat() { System.out.println("Changement vers EN_COURS"); }
}

class EtatEnCours implements EtatMachine {
    public void executer() { System.out.println("Machine en cours..."); }
    public void changerEtat() { System.out.println("Changement vers TERMINÉ"); }
}

class Machine {
    private EtatMachine etat;

    public Machine() {
        this.etat = new EtatArret();
    }

    public void changerEtat() {
        if (this.etat instanceof EtatArret) {
            this.etat = new EtatEnCours();
        } else if (this.etat instanceof EtatEnCours) {
            this.etat = new EtatTerminé();
        }
    }

    public void executer() {
        this.etat.executer();
    }
}
