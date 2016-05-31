package com.arkanoid.objekty;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Hlavný herný objekt, ktorý má spoločné veci, ktoré majú herné objekty
 *
 * @author Kubo
 */
public abstract class HernyObjekt {

    protected float x, y;
    protected ObjektId id;
    protected float velX = 0, velY = 0;
    protected int hp;
    protected int width, height;

    /**
     * Vytvorí základný objekt
     *
     * @param x - pozicia x
     * @param y - pozicia y
     * @param sirka - sirka objektu
     * @param vyska - vyska objektu
     * @param hp - pocet zivota
     */
    public HernyObjekt(float x, float y, int sirka, int vyska, int hp) {
        this.x = x;
        this.y = y;
        this.hp = hp;
        width = sirka;
        height = vyska;
    }

    /**
     * Aktualizácia pozície a kontrola kolízii
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    public abstract void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely);

    /**
     * Vykreslenie herného objektu
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    public abstract void render(Graphics g);

    /**
     * Vráti hranice herného objektu
     *
     * @return obdlžnik predstavujúci hranice
     */
    public abstract Rectangle getBounds();

    /**
     * Vezme hernému objektu určitý počet života
     *
     * @param pocet - počet, ktorý sa vezme
     */
    public void zoberHp(int pocet) {

    }

    /**
     * Vráti pozíciu x
     *
     * @return pozícia x
     */
    public float getX() {
        return x;
    }

    /**
     * Vráti pozíciu y
     *
     * @return pozícia y
     */
    public float getY() {
        return y;
    }

    /**
     * Nastaví pozíciu x
     *
     * @param x - nova pozícia x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Nastaví pozíciu y
     *
     * @param y - nova pozícia y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Vráti smer pohybu vodorovne
     *
     * @return - smer pohybu
     */
    public float getVelX() {
        return velX;
    }

    /**
     * Vráti smer pohybu zvisle
     *
     * @return - smer pohybu
     */
    public float getVelY() {
        return velY;
    }

    /**
     * Nastaví smer pohybu vodorovne
     *
     * @param velX - počet pixelov za obnovenie hry
     */
    public void setVelX(float velX) {
        this.velX = velX;
    }

    /**
     * Nastaví smer pohybu zvisle
     *
     * @param velY - počet pixelov za obnovenie hry
     */
    public void setVelY(float velY) {
        this.velY = velY;
    }

    /**
     * Vráti id objektu
     *
     * @return id objektu
     */
    public ObjektId getId() {
        return id;
    }

    /**
     * Nastaví id objektu
     *
     * @param id - nové id objektu
     */
    public void setId(ObjektId id) {
        this.id = id;
    }

}
