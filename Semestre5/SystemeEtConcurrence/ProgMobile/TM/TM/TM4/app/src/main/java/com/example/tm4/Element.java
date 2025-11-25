
package com.example.tm4;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Classe abstraite représentant un élément géométrique du terrain.
 *
 * Un Element possède :
 *   • un centre (coordonnées x,y) sous forme de float[2]
 *   • un rayon (taille) – float
 *   • une Paint décrivant son apparence à dessiner
 *
 * Les sous‑classes devront fournir les comportements spécifiques
 * (ex. Bille, Objectif, Obstacle).
 */
public abstract class Element {

    /* ---------- ATTRIBUTS ---------- */

    /** Coordonnées du centre : [0] = x , [1] = y */
    protected float[] centre;

    /** Rayon de l’élément */
    protected float rayon;

    /** Apparence (couleur, style…) */
    protected Paint paint;

    /* ---------- CONSTRUCTEURS ---------- */

    /**
     * Constructeur générique.
     *
     * @param x      coordonnée X du centre
     * @param y      coordonnée Y du centre
     * @param rayon  rayon de l’élément
     * @param couleur couleur (int ARGB) à appliquer à la Paint
     */
    public Element(float x, float y, float rayon, int couleur) {
        this.centre = new float[]{x, y};
        this.rayon  = rayon;
        this.paint  = new Paint();
        this.paint.setColor(couleur);
        // on peut ajouter d’autres paramètres (style, anti‑aliasing…)
    }

    /* ---------- ACCESSEURS / MUTATEURS ---------- */

    /** Retourne la coordonnée X du centre */
    public float getX() { return centre[0]; }

    /** Retourne la coordonnée Y du centre */
    public float getY() { return centre[1]; }

    /** Modifie la position de l’élément */
    public void setPosition(float x, float y) {
        centre[0] = x;
        centre[1] = y;
    }

    /** Retourne le rayon */
    public float getRayon() { return rayon; }

    /** Met à jour le rayon */
    public void setRayon(float r) { this.rayon = r; }

    /** Retourne la Paint utilisée pour dessiner l’élément */
    public Paint getPaint() { return paint; }

    /** Modifie la couleur de dessin */
    public void setCouleur(int couleur) {
        paint.setColor(couleur);
    }

    /* ---------- METHODE DE RENDU ---------- */

    /**
     * Dessine l'élément sur le canvas fourni.
     *
     * @param canvas Canvas sur lequel dessiner
     */
    public void draw(Canvas canvas) {
        if (canvas == null) return;
        canvas.drawCircle(centre[0], centre[1], rayon, paint);
    }

    /* ---------- Méthode d’outil (collision simple) ----------
     * Vous pouvez la laisser dans Element ou l'implémenter
     * dans Terrain selon votre architecture.
     */

    /** Vérifie si deux éléments se chevauchent */
    public boolean chevauche(Element autre) {
        float dx = this.centre[0] - autre.centre[0];
        float dy = this.centre[1] - autre.centre[1];
        float distanceSq = dx * dx + dy * dy;
        float rayonSomme = this.rayon + autre.rayon;
        return distanceSq < (rayonSomme * rayonSomme);
    }
}
