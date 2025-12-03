interface InterfaceA {
    void methodeA();
}

class ClasseB {
    public void methodeB() {
        System.out.println("Méthode B");
    }
}

class Adaptateur implements InterfaceA {
    private ClasseB b;

    public Adaptateur(ClasseB b) {
        this.b = b;
    }

    public void methodeA() {
        b.methodeB();
    }
}

// Usage
ClasseB b = new ClasseB();
InterfaceA a = new Adaptateur(b);
a.methodeA(); // Affiche "Méthode B"


//===========================================================//

interface InterfaceA {
    void methodeA();
    void methodeB();
}

class AncienneClasse {
    public void ancienneMethode() {
        System.out.println("Ancienne méthode");
    }
}

class Adaptateur implements InterfaceA {
    private AncienneClasse ancienne;

    public Adaptateur(AncienneClasse ancienne) {
        this.ancienne = ancienne;
    }

    @Override
    public void methodeA() {
        ancienne.ancienneMethode();
    }

    @Override
    public void methodeB() {
        System.out.println("Méthode B adaptée");
    }
}

// Usage
AncienneClasse old = new AncienneClasse();
InterfaceA adapt = new Adaptateur(old);
adapt.methodeA(); // Ancienne méthode
adapt.methodeB(); // Méthode B adaptée
