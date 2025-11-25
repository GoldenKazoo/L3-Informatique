package com.example.tm4;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Canvas;
import android.view.MotionEvent;


public class Terrain extends View
{
    private Bille bille = null;
    private Objectif objectif = null;
    private final ArrayList<Obstacle> obstacles = new ArrayList<>();

    public Terrain(Context ctx) { super(ctx); init(); }
    public Terrain(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }

    private void init() {
        setFocusable(true);
        setClickable(true);
    }


    public void setBille(Bille b) { this.bille = b; invalidate(); }
    public Bille getBille() { return bille; }

    public void setObjectif(Objectif o) { this.objectif = o; invalidate(); }
    public Objectif getObjectif() { return objectif; }

    public void addObstacle(Obstacle o) {
        obstacles.add(o);
        invalidate();
    }

    public ArrayList<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles); // copie pour éviter les modifs externes
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (objectif != null) objectif.draw(canvas);
        if (bille   != null) bille.draw(canvas);
        for (Obstacle o : obstacles) o.draw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            addObstacle(new Obstacle(x, y));
            return true;
        }
        return super.onTouchEvent(event);
    }

    public boolean checkCollisions() {
        if (bille == null) return false;

        // collision avec obstacles
        for (Obstacle o : obstacles) {
            if (bille.chevauche(o)) return true;
        }

        // collision avec objectif (exemple de victoire)
        if (objectif != null && bille.chevauche(objectif)) {
            return true;
        }
        return false;
    }

    public void moveBille(float dx, float dy) {
        if (bille == null) return;

        float newX = bille.getX() + dx;
        float newY = bille.getY() + dy;

        Bille temp = new Bille(newX, newY);
        temp.setRayon(bille.getRayon());

        for (Obstacle o : obstacles) {
            if (temp.chevauche(o)) {
                return;
            }
        }

        bille.setPosition(newX, newY);
        invalidate();
    }
}
