package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;

/**
 * Efekt, ktorý pridá hráčovi možnosť strieľania
 *
 * @author Kubo
 */
public class EfektPridanieStrielania extends Efekt {

    /**
     * Vytvorenie efektu, ktorý pridá strieľanie
     *
     * @param trvanie - trvanie efektu
     */
    public EfektPridanieStrielania(int trvanie) {
        super(trvanie);
    }

    /**
     * Aplikuje efekt na hráča, nastaví mu že môže strieľať
     *
     * @param hrac - hráč, ktorý hrá hru
     */
    @Override
    public void aplikujSa(Hrac hrac) {
        hrac.setStrielanie(true);
        casovac = new Trvanie(trvanie);
    }

    /**
     * Zruší hráčovi možnosť strieľania
     *
     * @param hrac - hráč, ktorý hrá hru
     */
    @Override
    public void zrusSa(Hrac hrac) {
        hrac.setStrielanie(false);
    }

}
