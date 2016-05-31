package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;

/**
 * Zakladna trieda pre efekt, ktorá obsahuje spoločné časti efektov
 *
 * @author Kubo
 */
public abstract class Efekt {

    protected int hodnota;
    protected int trvanie;
    protected Trvanie casovac;

    /**
     * Konstruktor efektu
     *
     * @param trvanie - trvanie, ktoré predstavuje koľko bude efekt trvat
     */
    public Efekt(int trvanie) {
        casovac = null;
        this.trvanie = trvanie;
    }

    /**
     * Nastavi hodnotu ktoru bude pridavať efekt
     *
     * @param hodnota - nová hodnota, ktorú bude pridávať
     */
    public void setHodnota(int hodnota) {
        this.hodnota = hodnota;
    }

    /**
     * Vráti hodnotu, ktorú pridáva efekt
     *
     * @return hodnota,ktorú pridáva efekt
     */
    public int getHodnota() {
        return hodnota;
    }

    /**
     * Vráti, či efekt skončil alebo nie
     *
     * @return skončenie efektu
     */
    public boolean skoncil() {
        return casovac.isKoniec();
    }

    /**
     * Pozastaví odpočet času trvania efektu
     */
    public void pause() {
        casovac.setPauza(true);
    }

    /**
     * Obnoví odpočitavanie trvania efektu
     */
    public void resume() {
        casovac.setPauza(false);
    }

    /**
     * Aplikovanie efektu na hráča alebo lopty
     *
     * @param hrac - hrac ktory hra hru, plosinka
     */
    public abstract void aplikujSa(Hrac hrac);

    /**
     * Zrušenie efektu z hráča alebo lopty
     *
     * @param hrac - hrac ktory hra hru, plosinka
     */
    public abstract void zrusSa(Hrac hrac);

}
