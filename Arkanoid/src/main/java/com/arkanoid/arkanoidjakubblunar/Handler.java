package com.arkanoid.arkanoidjakubblunar;

import com.arkanoid.gui.Main;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import com.arkanoid.objekty.Balicek;
import com.arkanoid.objekty.Blok;
import com.arkanoid.objekty.HernyObjekt;
import com.arkanoid.objekty.Hrac;
import com.arkanoid.objekty.Lopta;
import com.arkanoid.objekty.NeznicitelnyBlok;
import com.arkanoid.objekty.ObjektId;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Trieda, ktorá drží všetky herné objekty, z ktorých sa skladá level
 *
 * @author Kubo
 */
public class Handler {

    public static final Random rand = new Random();
    private final ArrayList<HernyObjekt> objekty;
    private final ArrayList<HernyObjekt> strely;
    private final Lopta l;
    private Blok[][] level;
    private final Hra hra;

    /**
     * Vytvorí handler a pridá mu loptu
     *
     * @param h - hráč, ktorý hrá hru
     * @param hra - aktualna hra
     */
    public Handler(Hrac h, Hra hra) {
        this.objekty = new ArrayList<>();
        this.strely = new ArrayList<>();
        this.hra = hra;
        this.l = new Lopta(h.getBoundsTop().x, h.getY() - 13, 13, h);
        objekty.add(h);
        objekty.add(l);

        level = new Blok[25][20];
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 5; j++) {
                level[i][j] = null;
            }
        }

    }

    /**
     * Metóda, ktorá povie aby sa herné objekty aktualizovali
     */
    public void tick() {

        for (int i = 0; i < objekty.size(); i++) {
            if (objekty.get(i).getId() == ObjektId.Blok) {
                Blok b = (Blok) objekty.get(i);
                if (b.isZniceny()) {
                    if (rand.nextInt(100) < 10) {
                        objekty.add(new Balicek(b.getX(), b.getY(), l));
                    }
                    hra.pridajSkore(b.getBody());
                    objekty.remove(b);
                }
            }
        }

        for (int i = 0; i < strely.size(); i++) {
            strely.get(i).tick(objekty, strely);
        }
        for (int i = 0; i < objekty.size(); i++) {
            objekty.get(i).tick(objekty, strely);
        }

    }

    /**
     * Vykreslenie všetkých herných objektov daného levelu
     *
     * @param g - grafický komponent
     */
    public void render(Graphics g) {
        for (int i = 0; i < objekty.size(); i++) {
            objekty.get(i).render(g);
        }

        for (int i = 0; i < strely.size(); i++) {
            strely.get(i).render(g);
        }

    }

    /**
     * Pridá herný objekt do herných objektov
     *
     * @param objekt - objekt na pridanie
     */
    public void pridajObjekt(HernyObjekt objekt) {
        objekty.add(objekt);
    }

    /**
     * Pridá strelu do zoznamu striel
     *
     * @param strela - strela, ktorú hráč vystrelil
     */
    public void pridajStrelu(HernyObjekt strela) {
        strely.add(strela);
    }

    /**
     * Načíta level zo suboru
     *
     * @param lvl - nazov levelu, ktorý ma byť nacitaný
     */
    public void nacitajLevel(String lvl) {
        level = new Blok[25][20];

        String s = "Levely/" + lvl + ".txt";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.loadResource(s)), "utf-8"))) {
            String line;
            int riadok = 0;
            while ((line = br.readLine()) != null) {
                String[] bloky = line.split(",");
                for (int i = 0; i < bloky.length; i++) {
                    if (!bloky[i].equals("0")) {
                        if (bloky[i].equals("-1")) {
                            Blok b = new NeznicitelnyBlok(20 + i * 30, 20 + riadok * 15, 30, 15);
                            level[i][riadok] = b;
                            objekty.add(b);
                        } else {
                            Blok b = new Blok(20 + i * 30, 20 + riadok * 15, 30, 15, Integer.parseInt(bloky[i]));
                            level[i][riadok] = b;
                            objekty.add(b);
                        }
                    }
                }
                riadok++;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Nepodarilo sa nacitat level!");
        }
    }

    /**
     * Skontroluje, či sú všetky políčka už zničené
     *
     * @return true,false o tom či su políčka zničené
     */
    public boolean skontrolujPolicka() {
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 5; j++) {
                if (level[i][j] != null && level[i][j].getId() == ObjektId.Blok) {
                    if (!level[i][j].isZniceny()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Spustí loptu, ktorá je zo začiatku umiestnená na plošine
     */
    public void pustiLoptu() {
        if (!l.isBezi()) {
            l.setBezi(true);
            Hra.zmenStav(StavHry.Bezi);
        }
    }

}
