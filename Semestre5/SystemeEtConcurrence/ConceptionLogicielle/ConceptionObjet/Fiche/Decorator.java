interface Boisson {
    double prix();
    String description();
}

class Café implements Boisson {
    public double prix() { return 2.0; }
    public String description() { return "Café"; }
}

class Sucre implements Boisson {
    private Boisson boisson;

    public Sucre(Boisson boisson) {
        this.boisson = boisson;
    }

    @Override
    public double prix() { return boisson.prix() + 0.5; }

    @Override
    public String description() { return boisson.description() + " + Sucre"; }
}

// Usage
Boisson cafe = new Café();
Boisson cafeSucre = new Sucre(cafe);
System.out.println(cafeSucre.description()); // Café + Sucre
System.out.println(cafeSucre.prix()); // 2.5


//===========================================================================================//

interface Boisson
{
    double prix();
    String description();
}

class Café implements Boisson {
    @Override public double prix() { return 2.0; }
    @Override public String description() { return "Café"; }
}

class Lait implements Boisson {
    private Boisson boisson;

    public Lait(Boisson boisson) {
        this.boisson = boisson;
    }

    @Override
    public double prix() { return boisson.prix() + 0.5; }

    @Override
    public String description() { return boisson.description() + " + Lait"; }
}

class Caramel implements Boisson {
    private Boisson boisson;

    public Caramel(Boisson boisson) {
        this.boisson = boisson;
    }

    @Override
    public double prix() { return boisson.prix() + 1.0; }

    @Override
    public String description() { return boisson.description() + " + Caramel"; }
}

// Usage
Boisson cafe = new Café();
Boisson cafeLait = new Lait(cafe);
Boisson cafeLaitCaramel = new Caramel(cafeLait);
System.out.println(cafeLaitCaramel.description()); // Café + Lait + Caramel
System.out.println(cafeLaitCaramel.prix()); // 3.5
