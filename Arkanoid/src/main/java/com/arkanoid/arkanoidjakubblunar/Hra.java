package com.arkanoid.arkanoidjakubblunar;

import com.arkanoid.gui.Main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.JOptionPane;
import com.arkanoid.objekty.Hrac;
import java.io.File;
import java.io.IOException;

/**
 * Hlavná trieda hry, ktorá riadi obnovy a vykresľovanie
 *
 * @author Kubo
 */
public class Hra extends Canvas implements Runnable {

    private static int SIRKA;
    private static int VYSKA;

    private static Okno okno;
    private static StavHry stav;
    private static Thread mainThread;

    private BufferStrategy bs;

    private KeyInput keyManager;
    private Handler handler;

    private Hrac hrac;

    private String level;
    private final String menoHraca;
    private int skore;
    private final Main main;

    /**
     * Vytvorí novú hru
     *
     * @param level - level ktorý sa načíta
     * @param meno - meno hráča
     * @param main - hlavný frame
     */
    public Hra(String level, String meno, Main main) {
        this.level = level;
        this.main = main;
        this.menoHraca = meno;
    }

    /**
     * Tu sa inicializuje hra
     */
    private void init() {
        hrac = new Hrac(SIRKA / 2 - 40, VYSKA - 5, 80, 15);
        skore = 0;

        handler = new Handler(hrac, this);
        handler.nacitajLevel(level);
        keyManager = new KeyInput(handler, hrac);
        this.addKeyListener(keyManager);

        if (bs == null) {
            this.createBufferStrategy(2);
            bs = this.getBufferStrategy();
        }

    }

    /**
     * Spustí hlavné vlákno hry v ktorom bezí hra
     */
    public void start() {
        if (stav == StavHry.Bezi) {
            return;
        }

        mainThread = new Thread(this);
        mainThread.start();
        stav = StavHry.Init;

    }

    /**
     * Riesi cas obnovovania, aktualizácie objektov a vykresľovania hry
     */
    public void run() {
        init();
        this.requestFocus();

        long lastTime = System.nanoTime();
        double pocetTikov = 60.0;
        double ns = 1000000000 / pocetTikov;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        boolean koniec = false;
        while (!koniec) {

            while (stav == StavHry.Bezi || stav == StavHry.Pozastavena || stav == StavHry.Init) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    tick();  
                    updates++;
                    delta--;
                }
                render();
                frames++;
               
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    frames = 0;     
                    updates = 0;
                }
            }
            
            // riesenie vecí ked sa hra dokoncí
            if (stav == StavHry.Prehra) {
                int umiestnenie = saveSkore();
                String popis = "Prehrali ste! " + getPopis(umiestnenie);

                String[] options = new String[]{"Ok"};
                int vysledok = JOptionPane.showOptionDialog(null, popis, "",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);

                okno.setVisible(false);
                mainThread = null;
                koniec = true;
            } else {//hrac vyhral
                String[] options = new String[]{"Pokračovať", "Skončiť"};
                int vysledok = JOptionPane.showOptionDialog(null, "Vyhrali ste! Skóre: " + skore + "", "",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]);
                
                //chce pokracovat
                if (vysledok == 0) {
                    int aktualnyLevel = Integer.parseInt(level);
                    if (aktualnyLevel < main.getMaxLevel()) {
                        aktualnyLevel++;
                        level = aktualnyLevel + "";
                        handler = new Handler(hrac, this);
                        handler.nacitajLevel(level);
                        keyManager.setHandler(handler);
                        stav = StavHry.Init;

                        if (main.getHracovMaxLevel() < aktualnyLevel) {

                            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("progress")), "utf-8"))) {
                                writer.write(level);
                                main.setHracovMaxLevel(aktualnyLevel);
                                writer.close();
                            } catch (IOException e) {
                            }
                        }
                    } else {

                        int umiestnenie = saveSkore();
                        String popis = "Gratulujem úspešne ste dokončili hru! " + getPopis(umiestnenie);
                        options = new String[]{"Späť do menu"};
                        vysledok = JOptionPane.showOptionDialog(null, popis, "",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options[0]);
                        okno.setVisible(false);
                        mainThread = null;
                        koniec = true;
                    }

                }
                
                //vypne dialogove okno alebo skonci
                if (vysledok == 1 || vysledok == -1) {

                    int aktualnyLevel = Integer.parseInt(level);
                    if (aktualnyLevel < main.getMaxLevel()) {
                        aktualnyLevel++;
                        level = aktualnyLevel + "";

                        if (main.getHracovMaxLevel() < aktualnyLevel) {
                            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream(new File("progress")), "utf-8"))) {
                                writer.write(level);
                                main.setHracovMaxLevel(aktualnyLevel);
                                writer.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                    int umiestnenie = saveSkore();
                    String popis = "Ukončili ste hru." + getPopis(umiestnenie);

                    options = new String[]{"Ok"};
                    vysledok = JOptionPane.showOptionDialog(null, popis, "",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, options, options[0]);

                    okno.setVisible(false);
                    mainThread = null;
                    koniec = true;

                }
            }
        }
    }

    /**
     * Metóda vráti popis, ktorý popisuje umiestnenie na ktorom sa hráč
     * umiestnil
     *
     * @return - popis, ktorý popisuje umiestnenie
     */
    private String getPopis(int umiestnenie) {
        String popis = "";
        if (umiestnenie > 0) {
            popis += "Umiestnili ste sa na " + umiestnenie + " mieste s " + skore + " bodmi.";
        } else {
            popis += "Bohužiaľ ste sa nedostali do top 10 s " + skore + " bodmi.";
        }
        return popis;
    }

    /**
     * Zistí hráčove umietnenie a ak sa umiestnil do rebrička uloží ho do suboru
     *
     * @return pozicia hráča, na ktorej sa umiestnil
     */
    private int saveSkore() {
        Rebricek r = Rebricek.dajInstanciu();
        Priecka p = new Priecka(menoHraca, skore);

        int umiestnenie = r.zistiUmiestnenie(p);
        if (umiestnenie > 0 && umiestnenie <= 10) {
            r.vlozZaznam(p, umiestnenie);
        }
        r.vlozDoSuboru();
        return umiestnenie <= 10 ? umiestnenie : -1;
    }

    /**
     * Metoda ktorá predstavuje hlavnu aktualizáciu hry
     */
    private void tick() {
        if (stav != StavHry.Pozastavena) {
            handler.tick();
            if (handler.skontrolujPolicka()) {
                stav = StavHry.Vyhra;
            }
        }

    }

    /**
     * Metóda, ktorá má na starosti vykresľovanie hry
     */
    private void render() {

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // vykreslovanie

        handler.render(g);

        Font stary = g.getFont();
        if (stav == StavHry.Init) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.setColor(Color.white);
            g.drawString("Stlačte Space pre vypustenie lopty.", 20, VYSKA - 80);
            g.setFont(stary);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.white);
        g.drawString(skore + "", 5, 30);
        g.setFont(stary);

        //
        g.dispose();
        bs.show();

    }

    /**
     * Zvysi skore aktualnej hry
     *
     * @param pocet - o kolko sa zvysi skore
     */
    public void pridajSkore(int pocet) {
        skore += pocet;
    }

    /**
     * Metóda ktorá mení stav hry
     *
     * @param paStav - stav, ktorý sa má nastaviť
     */
    public static void zmenStav(StavHry paStav) {
        stav = paStav;
        if (paStav == StavHry.Pozastavena) {
            String[] options = new String[]{"Pokračovať", "Skončiť"};
            int vysledok = JOptionPane.showOptionDialog(null, "Pauza", "",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (vysledok == 0 || vysledok == -1) {
                stav = StavHry.Bezi;
            }

            if (vysledok == 1) {
                mainThread.stop();
                okno.setVisible(false);
            }

        }
    }

    /**
     * Nastaví rozmery hry
     *
     * @param width - výška
     * @param height - šírka
     */
    public static void setRozmery(int width, int height) {
        SIRKA = width;
        VYSKA = height;
    }

    /**
     * Nastaví hre okno, na ktoré sa bude zobrazovať
     *
     * @param okno - okno na vykreslovanie
     */
    public static void setOkno(Okno okno) {
        Hra.okno = okno;
    }

    /**
     * Vráti šírku okna, na ktoré sa vykresľuje
     *
     * @return - sirka okna
     */
    public static int getSIRKA() {
        return SIRKA;
    }

    /**
     * Vráti výšku okna, na ktoré sa vykresľuje
     *
     * @return - vyska okna
     */
    public static int getVYSKA() {
        return VYSKA;
    }

    /**
     * Metóda vráti stav hry
     *
     * @return - stav hry
     */
    public static StavHry getStav() {
        return stav;
    }

}
