interface Animal {
    void parler();
}

class Chien implements Animal {
    public void parler() { System.out.println("Woof!"); }
}

class Chat implements Animal {
    public void parler() { System.out.println("Meow!"); }
}

class Fabricante {
    public static Animal creerAnimal(String type) {
        if ("chien".equals(type)) return new Chien();
        if ("chat".equals(type)) return new Chat();
        return null;
    }
}

// Usage
Animal a = Fabricante.creerAnimal("chien");
a.parler(); // Woof!

//====================================================================//
abstract class Animal {
    abstract void parler();
}

class Chien extends Animal { public void parler() { System.out.println("Woof!"); } }
class Chat extends Animal { public void parler() { System.out.println("Meow!"); } }
class Oiseau extends Animal { public void parler() { System.out.println("Coo!"); } }

interface Fabricante {
    Animal creer(String type);
}

class FabricanteAnimal implements Fabricante {
    @Override
    public Animal creer(String type) {
        switch (type.toLowerCase()) {
            case "chien": return new Chien();
            case "chat": return new Chat();
            case "oiseau": return new Oiseau();
            default: return null;
        }
    }
}

// Usage
Fabricante f = new FabricanteAnimal();
Animal a = f.creer("chien");
a.parler(); // Woof!

