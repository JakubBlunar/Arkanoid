package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;
import com.arkanoid.objekty.Lopta;

/**
 * Efekt ktorý zmení veľkosť lopty
 *
 * @author Kubo
 */
public class EfektVelkostLopty extends Efekt {

    private final Lopta l;

    /**
     * Vytvorí efekt, ktorý sa bude aplikovať na danu loptu
     *
     * @param trvanie - trvanie efektu
     * @param hodnota - o koľko sa zmení veľkosť lopty
     * @param l - lopta, ktorej rýchlosť sa bude upravovať
     */
    public EfektVelkostLopty(int trvanie, int hodnota, Lopta l) {
        super(trvanie);
        this.hodnota = hodnota;
        this.l = l;

    }

    /**
     * Zmení veľkosť lopty, ktorá je uložená v atribúte
     *
     * @param hrac - hráč, ktorý hrá hru, tu može byť null
     */
    @Override
    public void aplikujSa(Hrac hrac) {
        l.setWidth(l.getWidth() + hodnota);
        casovac = new Trvanie(trvanie);
    }

    /**
     * Vráti veľkosť lopte akú mala pred aplikovaním efektu
     *
     * @param hrac - hráč, ktorý hrá hru, tu môže byť null
     */
    @Override
    public void zrusSa(Hrac hrac) {
        l.setWidth(l.getWidth() - hodnota);
    }
}
