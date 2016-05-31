package com.arkanoid.efekty;

import com.arkanoid.arkanoidjakubblunar.Trvanie;
import com.arkanoid.objekty.Hrac;

/**
 * Efekt, ktorý rozšíri plošinku pomocou ktorej hráč odráža loptu
 *
 * @author Kubo
 */
public class EfektRozsirenieHraca extends Efekt {

    /**
     * Vytvorí efekt, ktorý rozširi plošinu o nejaku hodnotu
     *
     * @param trvanie - trvanie efektu
     * @param oKolko - počet pixelov o ktoré sa rozšíri plošinka
     */
    public EfektRozsirenieHraca(int trvanie, int oKolko) {
        super(trvanie);
        hodnota = oKolko;
    }

    /**
     * Rozšíri plošinku hráča o daný počet pixelov
     *
     * @param hrac - hráč, ktorý hrá hru
     */
    @Override
    public void aplikujSa(Hrac hrac) {
        hrac.setWidth(hrac.getWidth() + hodnota);
        casovac = new Trvanie(trvanie);
    }

    /**
     * Zúži plošinku hráča, opak aplikuj sa
     *
     * @param hrac - hráč, ktorý hrá hru
     */
    @Override
    public void zrusSa(Hrac hrac) {
        hrac.setX(hrac.getX() + (float) hodnota / 2);
        hrac.setWidth(hrac.getWidth() - hodnota);
    }

}
