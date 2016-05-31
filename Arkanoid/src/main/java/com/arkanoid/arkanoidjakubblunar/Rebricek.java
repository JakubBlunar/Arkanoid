package com.arkanoid.arkanoidjakubblunar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Trieda predstavuje herný rebríček ktorý je ulozený v textovom subore. Narába
 * s ním , mení poradia a nasledne zapíše do suboru.
 *
 * @author Jakub
 */
public class Rebricek {

    private static final Rebricek aInstancia = new Rebricek();

    private File aSubor;
    private Priecka[] aPoradie;

    // Konstruktor vytvorí rebricek, pokusi sa nacitat data zo suboru. Ak sa mu to nepodari tak zresetuje data a nacita znovu.
    private Rebricek() {

        aSubor = new File("rebricek");
        aPoradie = new Priecka[10];
        try {
            this.nacitajZoSuboru();
        } catch (FileNotFoundException c) {
            try {
                this.resetniPoradia();
                this.nacitajZoSuboru();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Vráti rebricek.
     *
     * @return Rebricek
     */
    public static Rebricek dajInstanciu() {
        return aInstancia;
    }

    /**
     * Vlozí priecku na jej správne umiestnenie
     *
     * @param paPriecka - priečka, ktorá bude vložená
     * @param paPozicia - pozícia, na ktorú sa vloží
     */
    public void vlozZaznam(Priecka paPriecka, int paPozicia) {
        if (paPozicia > -1 && paPozicia < 9) {
            for (int i = aPoradie.length - 1; i >= paPozicia; i--) {
                aPoradie[i] = aPoradie[i - 1];
            }
        }
        aPoradie[paPozicia - 1] = paPriecka;
    }

    /**
     * Zistí na akom umiestnení sa umiestnil hráč . ak sa neumiestnil v rebričku
     * vráti -1
     *
     * @param paPriecka - priecka ktorá má meno a skore ktoré sa porovnáva
     * @return - umiestnenie
     */
    public int zistiUmiestnenie(Priecka paPriecka) {
        int pozicia = 0;
        boolean najdene = false;

        while (najdene == false) {
            if (pozicia == 10) {
                return -1;
            }
            if (aPoradie[pozicia].dajSkore() > paPriecka.dajSkore()) {

            } else {
                najdene = true;
            }
            pozicia++;
        }
        return pozicia;
    }

    /**
     * vráti textovu reprezentáciu rebríčka
     *
     * @return rebricek z poradiami
     */
    @Override
    public String toString() {
        String vratene = "   Meno - Body\n";
        for (int i = 0; i < 10; i++) {
            vratene += (i + 1) + ". " + aPoradie[i].toString() + "\n";
        }

        return vratene;
    }

    /**
     * Uloží rebríček do súboru.
     */
    public void vlozDoSuboru() {
        PrintWriter zapisovac = null;
        try {
            zapisovac = new PrintWriter(aSubor, "utf-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        }

        if (zapisovac != null) {
            for (int i = 0; i < 10; i++) {
                zapisovac.println(aPoradie[i].dajMeno() + " " + aPoradie[i].dajSkore());
            }
            zapisovac.close();
        } 
    }

    /**
     * Nastavi vsetky poradia na prazdne 0
     */
    private void resetniPoradia() {
        for (int i = 0; i < 10; i++) {
            aPoradie[i] = new Priecka("Prazdne", 0);
        }
        this.vlozDoSuboru();
    }

    /**
     * Pokúsi sa nacítať rebricek zo suboru
     *
     * @throws FileNotFoundException ked subor neexistuje a nedá sa vytvoriť
     * citac
     */
    private void nacitajZoSuboru() throws FileNotFoundException {
        try (Scanner citac = new Scanner(aSubor, "utf-8")) {
            for (int i = 0; i < 10; i++) {
                String meno = citac.next();
                int skore = citac.nextInt();
                aPoradie[i] = new Priecka(meno, skore);
            }

        } catch (FileNotFoundException ex) {
            this.resetniPoradia();
            this.nacitajZoSuboru();
        }

    }
}
