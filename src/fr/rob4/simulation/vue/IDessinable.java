package fr.rob4.simulation.vue;

import java.awt.Graphics2D;

public interface IDessinable {
    /**
     * Dessine l'�l�ment
     * @param graphics2D Le graphics2D a utiliser pour le dessin
     * @param echelle L'�chelle � laquelle dessiner (en p/m�tre)
     */
    void dessine(Graphics2D graphics2D, double echelle);
}
