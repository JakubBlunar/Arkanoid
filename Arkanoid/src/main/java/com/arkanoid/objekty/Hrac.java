package com.arkanoid.objekty;

import com.arkanoid.efekty.Efekt;
import com.arkanoid.arkanoidjakubblunar.Hra;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Trieda, ktorá je plošinkou a predstavuje hráča
 *
 * @author Kubo
 */
public class Hrac extends HernyObjekt {

    private boolean strielanie;
    private int rychlost = 5;
    private final LinkedList<Efekt> efekty;

    /**
     * Vytvorí novú plošinku
     *
     * @param x - pozícia x
     * @param y - pozícia y
     * @param sirka - šírka
     * @param vyska - výška
     */
    public Hrac(float x, float y, int sirka, int vyska) {
        super(x, y, sirka, vyska, 5);
        setId(ObjektId.Hrac);
        efekty = new LinkedList<>();
        strielanie = false;
    }

    /**
     * Aktualizácia pozície a kontrola kolízii a rušenie efektov
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    @Override
    public void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely) {
        x += velX * rychlost;
        if (x <= 0) {
            x = 1;
        }
        if (x + width > Hra.getSIRKA() + 3) {
            x = Hra.getSIRKA() - width + 3;
        }

        for (int i = 0; i < efekty.size(); i++) {
            Efekt e = efekty.get(i);
            if (e.skoncil()) {
                e.zrusSa(this);
                efekty.remove(e);
            }
        }

    }

    /**
     * Vykreslenie hráča
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    @Override
    public void render(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        if (strielanie) {
            g.fillRect((int) x, (int) y - 3, 3, 3);
            g.fillRect((int) x + width - 3, (int) y - 3, 3, 3);
        }
        /*
        g2d.setColor(Color.yellow);
        g2d.draw(getBoundsTop());
        g2d.setColor(Color.green);
        g2d.draw(getBoundsTopLeft());
        g2d.setColor(Color.red);
        g2d.draw(getBoundsTopRight());*/

    }

    /**
     * Pridá efekt hráčovi
     *
     * @param efekt - efekt na pridanie
     */
    public void pridajEfekt(Efekt efekt) {
        efekt.aplikujSa(this);
        efekty.add(efekt);

    }

    /**
     * Vráti hranice hráča
     *
     * @return obdlžnik predstavujúci hranice
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    /**
     * Vráti ľavú hornú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje ľavú hornú časť
     */
    public Rectangle getBoundsTopLeft() {
        return new Rectangle((int) (x), (int) y, (int) 18, 4);
    }

    /**
     * Vráti pravú hornú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje pravú hornú časť
     */
    public Rectangle getBoundsTopRight() {
        return new Rectangle((int) (x + width - 18), (int) y, (int) 18, 4);
    }

    /**
     * Vráti hornú hranicu bloku
     *
     * @return obdlznik, ktorý predstavuje hornú hranu
     */
    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x + 18), (int) y, (int) width - 36, 2);
    }

    /**
     * Nastaví šírku plošinky
     *
     * @param width - nová šírka
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Vráti širku plošinky
     *
     * @return šírka plošinky
     */
    public int getWidth() {
        return width;
    }

    /**
     * Vráti rýchlosť plošinky
     *
     * @return rýchlosť
     */
    public int getRychlost() {
        return rychlost;
    }

    /**
     * Nastaví rýchlosť plošinky
     *
     * @param rychlost - nová rýchlosť
     */
    public void setRychlost(int rychlost) {
        this.rychlost = rychlost;
    }

    /**
     * Nastaví hráčovi strieľanie
     *
     * @param strielanie - nová hodnota
     */
    public void setStrielanie(boolean strielanie) {
        this.strielanie = strielanie;
    }

    /**
     * Vráti, či môže hráč strieľať alebo nie
     *
     * @return či môže hráč strieľať
     */
    public boolean isStrielanie() {
        return strielanie;
    }

}
