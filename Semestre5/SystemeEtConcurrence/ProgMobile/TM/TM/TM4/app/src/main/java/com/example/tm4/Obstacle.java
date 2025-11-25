package com.example.tm4;

public class Obstacle extends Element {

    public static final int  COULEUR_DEF = 0xFFFF0000; // rouge opaque
    public static final float RAYON_DEF  = 25f;

    /**
     * Construction avec rayon par défaut (ou spécifié).
     */
    public Obstacle(float x, float y) {
        super(x, y, RAYON_DEF, COULEUR_DEF);
    }

    public Obstacle(float x, float y, float rayon) {
        super(x, y, rayon, COULEUR_DEF);
    }
}
