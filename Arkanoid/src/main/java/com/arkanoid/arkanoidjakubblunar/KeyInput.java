package com.arkanoid.arkanoidjakubblunar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.arkanoid.objekty.Hrac;
import com.arkanoid.objekty.Strela;

/**
 * Trieda, ktorá rieši vstup z klávesnice od použivateľa
 *
 * @author Kubo
 */
public class KeyInput extends KeyAdapter {

    private Handler handler;
    private final Hrac h;

    /**
     * Vytvorenie manažera klavesnice
     *
     * @param handler - kontainer objektov
     * @param h - hráč
     */
    public KeyInput(Handler handler, Hrac h) {
        this.handler = handler;
        this.h = h;
    }

    /**
     * Metóda, ktorá sa vykoná pri stlačení klavesy
     *
     * @param e - event stlačenej klávesy
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            handler.pustiLoptu();
        }

        if (key == KeyEvent.VK_RIGHT) {
            h.setVelX(+1);
        }

        if (key == KeyEvent.VK_LEFT) {
            h.setVelX(-1);
        }

        if (key == KeyEvent.VK_ESCAPE) {
            Hra.zmenStav(StavHry.Pozastavena);
        }
    }

    /**
     * Metóda, ktorá sa vykoná pri pustení klávesy
     *
     * @param e - event pustenej klávesy
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            h.setVelX(0);
        }
        if (key == KeyEvent.VK_RIGHT) {
            h.setVelX(0);
        }

        if (key == KeyEvent.VK_SPACE) {
            if (h.isStrielanie()) {
                handler.pridajStrelu(new Strela(h.getX(), h.getY() - 5));
                handler.pridajStrelu(new Strela(h.getX() + h.getWidth() - 3, h.getY() - 5));
            }
        }

    }

    /**
     * Metóda nastaví handler na nový
     *
     * @param handler - nový handler objektov
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}
