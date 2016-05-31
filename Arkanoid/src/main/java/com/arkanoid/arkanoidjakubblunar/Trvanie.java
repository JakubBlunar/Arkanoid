package com.arkanoid.arkanoidjakubblunar;

/**
 * Trieda trvanie je časovač, ktorý odpočítava sekundy od určiteho počtu
 *
 * @author Kubo
 */
public class Trvanie implements Runnable {

    private boolean koniec;
    private int pocSekund;
    private final Thread t;
    private boolean pauza;

    /**
     * Vytvorí nový časovač ktorý bude odpočítavať
     *
     * @param pocSekund - počet sekund na odpočítanie
     */
    public Trvanie(int pocSekund) {
        this.pocSekund = pocSekund;
        if (pocSekund <= 0) {
            koniec = true;
        }
        pauza = false;

        t = new Thread(this);
        t.start();

    }

    /**
     * Vráti či už odpočítavanie skončilo
     *
     * @return - koniec ?
     */
    public boolean isKoniec() {
        return koniec;
    }

    /**
     * Pozastaví alebo obnoví časovač
     *
     * @param pauza - pozastavenie alebo obnovenie
     */
    public void setPauza(boolean pauza) {
        this.pauza = pauza;
    }

    @Override
    public void run() {

        while (pocSekund > 0) {
            if (!pauza) {
                try {
                    pocSekund--;
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
        koniec = true;
        t.stop();
    }

}
