package com.arkanoid.objekty;

import com.arkanoid.efekty.Efekt;
import com.arkanoid.efekty.EfektPridanieStrielania;
import com.arkanoid.efekty.EfektRozsirenieHraca;
import com.arkanoid.efekty.EfektRychlostLopty;
import com.arkanoid.efekty.EfektVelkostLopty;
import com.arkanoid.efekty.EfektZrychlenieHraca;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Objekt, ktorý padá nadol a keď ho hráč chytí dostane nejaký efekt
 *
 * @author Kubo
 */
public class Balicek extends HernyObjekt {

    private static final Random rand = new Random();
    private Efekt efekt;

    /**
     * Vytvoré nový balíček
     *
     * @param x - pozicia x
     * @param y - pozicia y
     * @param l - lopta, s ktorou hráč hrá
     */
    public Balicek(float x, float y, Lopta l) {
        super(x, y, 20, 20, 5);
        setId(ObjektId.Balicek);
        velY = 1 + rand.nextInt(3);

        int c = rand.nextInt(5);
        switch (c) {
            case 0:
                efekt = new EfektRozsirenieHraca(10 + rand.nextInt(20), 20 + rand.nextInt(20));
                break;
            case 1:
                efekt = new EfektZrychlenieHraca(10 + rand.nextInt(10), 1 + rand.nextInt(2));
                break;
            case 2:
                efekt = new EfektRychlostLopty(10 + rand.nextInt(10), 1 - rand.nextInt(3), l);
                break;
            case 3:
                efekt = new EfektVelkostLopty(10 + rand.nextInt(10), 10 - rand.nextInt(16), l);
                break;
            case 4:
                efekt = new EfektPridanieStrielania(10 + rand.nextInt(20));
                break;
            default:
                break;
        }

    }

    /**
     * Aktualizácia pozície a kontrola kolízii
     *
     * @param objekty - herné objekty, ktoré sú v hre
     * @param strely - strely, ktoré vystrelil hráč
     */
    @Override
    public void tick(ArrayList<HernyObjekt> objekty, ArrayList<HernyObjekt> strely) {
        y += velY;

        for (int i = 0; i < objekty.size(); i++) {
            HernyObjekt objekt = objekty.get(i);
            if (objekt.getId() == ObjektId.Hrac) {
                if (getBounds().intersects(objekt.getBounds())) {
                    Hrac h = (Hrac) objekt;
                    h.pridajEfekt(efekt);
                    objekty.remove(this);
                }
            }
        }

        if (y >= 650) {
            objekty.remove(this);
        }
    }

    /**
     * Vykreslí balíček na danej pozicii
     *
     * @param g - graficky komponent ktorý vykresľuje
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRoundRect((int) x, (int) y, width, width, 5, 5);
    }

    /**
     * Vráti hranice balíčku
     *
     * @return obdlžnik predstavujúci hranice
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

}
