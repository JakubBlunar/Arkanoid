package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;
import com.arkanoid.objekty.Lopta;

/**
 * Efekt, ktorý modifikuje rýchlosť lopty
 *
 * @author Kubo
 */
public class EfektRychlostLopty extends Efekt {

    private final Lopta l;

    /**
     * Vytvorí efekt, ktorý sa bude aplikovať na danu loptu
     *
     * @param trvanie - trvanie efektu
     * @param hodnota - o koľko sa zmení rýchlosť lopty
     * @param l - lopta, ktorej rýchlosť sa bude upravovať
     */
    public EfektRychlostLopty(int trvanie, int hodnota, Lopta l) {
        super(trvanie);
        this.hodnota = hodnota;
        this.l = l;

    }

    /**
     * Zmení rýchlosť lopty ktorá je uložená v atribúte
     *
     * @param hrac - hráč, ktorý hrá hru, tu može byť null
     */
    @Override
    public void aplikujSa(Hrac hrac) {
        l.setRychlost(l.getRychlost() + hodnota);
        casovac = new Trvanie(trvanie);
    }

    /**
     * Vráti rýchlosť lopty akú mala pred aplikovaním efektu
     *
     * @param hrac - hráč, ktorý hrá hru, tu môže byť null
     */
    @Override
    public void zrusSa(Hrac hrac) {
        l.setRychlost(l.getRychlost() - hodnota);
    }

}
