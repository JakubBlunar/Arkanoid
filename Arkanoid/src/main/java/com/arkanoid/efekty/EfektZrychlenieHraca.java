package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;

/**
 * Efekt, ktorý zrýchli plošinku hráča
 *
 * @author Kubo
 */
public class EfektZrychlenieHraca extends Efekt {

    /**
     * Vytvorí efekt, ktorý zrýchli plošinku hráča
     *
     * @param trvanie - koľko bude trvať efekt
     * @param oKolko - o koľko ju zrýchli
     */
    public EfektZrychlenieHraca(int trvanie, int oKolko) {
        super(trvanie);
        hodnota = oKolko;
    }

    /**
     * Zvýši rýchlosť hráčovej plošinky
     *
     * @param hrac - plošinka, ktorá predstavuje hráča
     */
    @Override
    public void aplikujSa(Hrac hrac) {
        hrac.setRychlost(hrac.getRychlost() + hodnota);
        casovac = new Trvanie(trvanie);
    }

    /**
     * Vráti rýchlosť plošinky na povodnú hodnotu aká bola pred aplikovaním
     *
     * @param hrac - plošinka, ktorá predstavuje hráča
     */
    @Override
    public void zrusSa(Hrac hrac) {
        hrac.setRychlost(hrac.getRychlost() - hodnota);
    }

}
