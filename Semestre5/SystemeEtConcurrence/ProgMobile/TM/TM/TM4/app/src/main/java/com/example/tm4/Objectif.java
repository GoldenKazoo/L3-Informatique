package com.example.tm4;

public class Objectif extends Element
{
    public static final int  COULEUR_DEF = 0xFF00FF00; // vert opaque
    public static final float RAYON_DEF  = 35f;

    public Objectif(float x, float y) {
        super(x, y, RAYON_DEF, COULEUR_DEF);
    }

    public Objectif(float x, float y, float rayon) {
        super(x, y, rayon, COULEUR_DEF);
    }
}
