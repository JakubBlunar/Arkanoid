package com.arkanoid.objekty;

/**
 * Blok ktorý sa nedá zničiť
 *
 * @author Kubo
 */
public class NeznicitelnyBlok extends Blok {

    /**
     * Vytvorí nový nezničiteľný blok
     *
     * @param x - pozícia x
     * @param y - pozícia y
     * @param sirka - širka
     * @param vyska - výška
     */
    public NeznicitelnyBlok(float x, float y, int sirka, int vyska) {
        super(x, y, sirka, vyska, 5);
        setId(ObjektId.NeznicitelnyBlok);
    }

    /**
     * Pri braní hp nezoberie nič
     *
     * @param pocet - počet hp na zobranie
     */
    @Override
    public void zoberHp(int pocet) {

    }

}
