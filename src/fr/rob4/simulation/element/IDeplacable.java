package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;

public interface IDeplacable extends IElement {
    /**
     * Fait tourner l'élément autour d'un point
     *
     * @param angle  L'angle de rotation (en radian)
     * @param centre Le centre de la rotation
     */
    void rotation(double angle, Point2D centre);

    /**
     * Translate l'élément
     *
     * @param deplacement Le déplacement à effectuer
     */
    void translation(Vecteur2D deplacement);
}
