package com.arkanoid.objekty;

import com.arkanoid.arkanoidjakubblunar.Handler;
import com.arkanoid.arkanoidjakubblunar.Hra;
import com.arkanoid.arkanoidjakubblunar.StavHry;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Lopta je lopta, ktorú odráža hráč a ničí ňou bloky
 *
 * @author Kubo
 */
public class Lopta extends HernyObjekt {

    private boolean bezi = false;
    private int rychlost = 4;
    private int poskodenie = 5;
    private Hrac h;

    /**
     * Vytvorí novú loptu
     *
     * @param x - pozícia x
     * @param y - pozícia y
     * @param polomer - polomer lopty
     * @param h - hráč
     */
    public Lopta(float x, float y, int polomer, Hrac h) {
        super(x, y, polomer, polomer, 2);
        this.h = h;
        setId(ObjektId.Lopta);
        bezi = false;
        velX = 0;
        velY = 0;
    }

    /**
     * Aktualizácia pozície a kontrola kolízii
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    @Override
    public void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely) {
        x += velX * rychlost;
        y += velY * rychlost;

        if (!bezi) {
            x = h.getBoundsTop().x;
            y = h.getBoundsTopRight().y - width;
        }

        for (int i = 0; i < objekty.size(); i++) {
            HernyObjekt objekt = objekty.get(i);
            if (objekt.getId() == ObjektId.Blok || objekt.getId() == ObjektId.NeznicitelnyBlok) {
                Blok b = (Blok) objekt;
                if (!b.isZniceny()) {
                    if (getBounds().intersects(b.getBoundsBottom())) {
                        velY = 1;
                        if (b.getId() == ObjektId.Blok) {
                            b.zoberHp(poskodenie);
                        }

                    }

                    if (getBounds().intersects(b.getBoundsTop())) {
                        velY = -1;
                        if (b.getId() == ObjektId.Blok) {
                            b.zoberHp(poskodenie);
                        }
                    }

                    if (getBounds().intersects(b.getBoundsLeft())) {
                        velX = -1;
                        if (b.getId() == ObjektId.Blok) {
                            b.zoberHp(poskodenie);
                        }

                    }

                    if (getBounds().intersects(b.getBoundsRight())) {
                        velX = 1;
                        if (b.getId() == ObjektId.Blok) {
                            b.zoberHp(poskodenie);
                        }
                    }
                }
            }
            if (objekt.getId() == ObjektId.Hrac) {
                Hrac h = (Hrac) objekt;

                if (getBounds().intersects(h.getBoundsTop())) {
                    x += h.getVelX() * 5;
                    velY = -1;
                }

                if (getBounds().intersects(h.getBoundsTopLeft())) {
                    x += h.getVelX() * 5;
                    velY = -1;
                    velX = -1;
                }

                if (getBounds().intersects(h.getBoundsTopRight())) {
                    x += h.getVelX() * 5;
                    velY = -1;
                    velX = 1;
                }

            }
        }

        if (y >= 600) {
            Hra.zmenStav(StavHry.Prehra);
        }

        if (y <= 0) {
            y = 0;
            velY = 1;
        }

        if (x <= 0) {
            x = 0;
            velX = 1;
        }

        if (x + width >= Hra.getSIRKA() + 10) {
            x = Hra.getSIRKA() - width + 10;
            velX = -1;
        }

    }

    /**
     * Vykreslí loptu na danej pozicii
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval((int) x, (int) y, width, width);

    }

    /**
     * Vráti hranice lopty
     *
     * @return obdlžnik predstavujúci hranice
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) width);
    }

    /**
     * Vráti či sa lopta pohybuje alebo nie
     *
     * @return true, false
     */
    public boolean isBezi() {
        return bezi;
    }

    /**
     * Nastaví lopte pohyb alebo ju zastaví
     *
     * @param bezi - pohyb
     */
    public void setBezi(boolean bezi) {
        if (bezi) {
            int c = Handler.rand.nextInt(2);
            if (c == 0) {
                velX = -1;
            } else {
                velX = 1;
            }

            velY = -1;
            this.bezi = bezi;
        } else {
            velY = 0;
            velY = 0;
            this.bezi = bezi;
        }
    }

    /**
     * Vráti rýchlosť lopty
     *
     * @return rýchlosť lopty
     */
    public int getRychlost() {
        return rychlost;
    }

    /**
     * Nastaví rýchlosť lopty
     *
     * @param rychlost - nová rýchlosť
     */
    public void setRychlost(int rychlost) {
        this.rychlost = rychlost;
    }

    /**
     * Nastaví polomer lopty
     *
     * @param width - nový polomer
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Vráti polomer lopty
     *
     * @return - polomer lopty
     */
    public int getWidth() {
        return width;
    }

}
