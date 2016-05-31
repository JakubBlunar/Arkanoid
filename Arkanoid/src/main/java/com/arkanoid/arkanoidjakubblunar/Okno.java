package com.arkanoid.arkanoidjakubblunar;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Trieda predstavuje okno hry, na ktore sa vykresľuje
 *
 * @author Kubo
 */
public class Okno {

    JFrame f;

    /**
     * Vytvorí Jframe s danými parametrami
     *
     * @param w - širka okna
     * @param h - vyška okna
     * @param titulok - titulok
     * @param game - hra
     */
    public Okno(int w, int h, String titulok, Hra game) {
        game.setPreferredSize(new Dimension(w, h));
        game.setMaximumSize(new Dimension(w, h));
        game.setMinimumSize(new Dimension(w, h));

        f = new JFrame(titulok);
        f.add(game);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    /**
     * Nastaví viditeľnosť okna
     *
     * @param paNa - viditeľnosť
     */
    public void setVisible(boolean paNa) {
        f.setVisible(paNa);
    }

}
