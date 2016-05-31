package com.arkanoid.objekty;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Blok tvorí kocku ktorú musí hráč zničiť pomocou lopty
 *
 * @author Kubo
 */
public class Blok extends HernyObjekt {

    private static final Random rand = new Random();
    private boolean zniceny;
    private final int body;

    private final Color c;

    /**
     * Vytvorí nový blok
     *
     * @param x - pozicia x
     * @param y - pozícia y
     * @param width - sirka
     * @param height - vyska
     * @param hp - pocet zivota
     */
    public Blok(float x, float y, int width, int height, int hp) {
        super(x, y, width, height, hp);
        setId(ObjektId.Blok);
        body = hp;

        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        Color mix = new Color(176, 224, 230);
        r = (int) ((r + mix.getRed()) / 2);
        g = (int) ((g + mix.getGreen()) / 2);
        b = (int) ((b + mix.getBlue()) / 2);
        c = new Color(r, g, b);

    }

    /**
     * Vráti, koľko bodov získa hráč za zničenie bloku
     *
     * @return počet bodov
     */
    public int getBody() {
        return body;
    }

    /**
     * Aktualizácia pozície a kontrola kolízii
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    @Override
    public void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely) {

    }

    /**
     * Vykreslenie bloku
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    @Override
    public void render(Graphics g) {
        if (!zniceny) {

            g.setColor(c);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
    }

    /**
     * Vráti hranice bloku
     *
     * @return obdlžnik predstavujúci hranice
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Vráti hornú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje hornú hranu
     */
    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x + 1), (int) y, (int) width - 2, 2);
    }

    /**
     * Vráti ľavú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje ľavú hranu
     */
    public Rectangle getBoundsLeft() {
        return new Rectangle((int) (x), (int) y + 1, 2, (int) height - 2);
    }

    /**
     * Vráti pravú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje pravú hranu
     */
    public Rectangle getBoundsRight() {
        return new Rectangle((int) (x + width - 2), (int) y + 1, 2, (int) height - 2);
    }

    /**
     * Vráti dolnú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje dolnú hranu
     */
    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (x + 1), (int) (y + height - 2), (int) width - 2, (int) 2);
    }

    /**
     * Vráti, či je blok zničený alebo nie
     *
     * @return true,false či je zničený
     */
    public boolean isZniceny() {
        return zniceny;
    }

    /**
     * Vezme bloku určitý počet života
     *
     * @param pocet - počet ktorý sa mu vezme
     */
    @Override
    public void zoberHp(int pocet) {
        hp -= pocet;
        if (hp <= 0) {
            zniceny = true;
        }
    }

}
