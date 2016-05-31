package com.arkanoid.arkanoidjakubblunar;

/**
 * Priecka predstavuje 1 umiestnenie v rebricku. Tvorí ju meno a skore
 *
 * @author Kubo
 */
public class Priecka {

    private final String aMeno;
    private final int aSkore;

    /**
     * Vytvorí novú priečku
     *
     * @param paMeno - meno
     * @param paSkore - skore hráča
     */
    public Priecka(String paMeno, int paSkore) {
        aMeno = paMeno;
        aSkore = paSkore;
    }

    /**
     * vráti textovu reprezentaciu priečky
     *
     * @return - textova reprezentacia priečky
     */
    @Override
    public String toString() {
        return aMeno + " - " + aSkore;
    }

    /**
     * Dá meno ktoré obsahuje priečka
     *
     * @return meno
     */
    public String dajMeno() {
        return aMeno;
    }

    /**
     * Vráti skore ktoré obsahuje priečka
     *
     * @return Cislo skore
     */
    public int dajSkore() {
        return aSkore;
    }

}
