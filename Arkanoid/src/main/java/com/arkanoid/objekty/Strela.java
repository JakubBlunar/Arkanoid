package com.arkanoid.objekty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Trieda predstavuje strelu, ktorú vystrelí hráč
 *
 * @author Kubo
 */
public class Strela extends HernyObjekt {

    /**
     * Vytvorí novú strelu na pozicii
     *
     * @param x - pozícia x
     * @param y - pozícia y
     */
    public Strela(float x, float y) {
        super(x, y, 3, 3, 5);
        velY = -3;
    }

    /**
     * Aktualizácia pozície a kontrola kolízii a rušenie efektov
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    @Override
    public void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely) {
        y += velY;

        for (int i = 0; i < objekty.size(); i++) {
            HernyObjekt objekt = objekty.get(i);
            if (objekt.getId() == ObjektId.Blok || objekt.getId() == ObjektId.NeznicitelnyBlok) {
                Blok b = (Blok) objekt;
                if (!b.isZniceny()) {
                    if (getBounds().intersects(b.getBoundsBottom())) {
                        if (b.getId() == ObjektId.Blok) {
                            b.zoberHp(1);
                        }
                        strely.remove(this);
                    }
                }
            }
        }

        if (y < -50) {
            objekty.remove(this);
        }

    }

    /**
     * Vykreslenie strely
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, width, width);
    }

    /**
     * Vráti hranice strely
     *
     * @return obdlžnik predstavujúci hranice
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, width);
    }

}
