package fr.rob4.simulation.vue;

import java.awt.Graphics2D;

public interface IDessinateur<T> {
    /**
     * Dessine l'élément
     *
     * @param graphics2D Le graphics2D a utiliser pour le dessin
     * @param echelle    L'échelle à laquelle dessiner (en p/mètre)
     * @param element    L'élément à dessiner
     */
    void dessine(Graphics2D graphics2D, double echelle, T element);

    /**
     * Obtient le type du dessinateur
     *
     * @return La classe du type dessinable
     */
    Class<T> getType();
}
