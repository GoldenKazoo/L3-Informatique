package com.example.tm4;

/**
 * Bille : élément mobile que l’on fait glisser.
 */
public class Bille extends Element {

    /* Couleur et rayon par défaut (peuvent être modifiés si besoin) */
    public static final int COULEUR_DEF = 0xFF0000FF; // bleu opaque
    public static final float RAYON_DEF  = 30f;

    /**
     * Constructeur complet : position, rayon & couleur.
     * Utilise les valeurs par défaut pour simplifier l’appel.
     */
    public Bille(float x, float y) {
        super(x, y, RAYON_DEF, COULEUR_DEF);
    }

    /** Variante si on veut changer le rayon à la volée */
    public Bille(float x, float y, float rayon) {
        super(x, y, rayon, COULEUR_DEF);
    }
}
