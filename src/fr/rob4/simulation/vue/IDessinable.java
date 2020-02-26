package fr.rob4.simulation.vue;

import java.awt.Graphics2D;

public interface IDessinable {
    /**
     * Dessine l'élément
     * @param graphics2D Le graphics2D a utiliser pour le dessin
     * @param echelle L'échelle à laquelle dessiner (en p/mètre)
     */
    void dessine(Graphics2D graphics2D, double echelle);
}
