package Pont;

public class Main {
    public static void main(String[] args) {
        Pont pont = new Pont();
        Voiture[] voitures = new Voiture[5];

        for (int i = 0; i < 5; i++) {
            boolean direction = (i % 2 == 0); // true = sud, false = nord
            voitures[i] = new Voiture(pont, i, direction);
            voitures[i].start();
        }
    }
}
