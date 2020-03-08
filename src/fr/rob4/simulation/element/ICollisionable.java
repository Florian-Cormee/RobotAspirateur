package fr.rob4.simulation.element;

import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

public interface ICollisionable extends IElement {
    /**
     * Prédicat d'une collision
     * <p>
     * Test la superposition des formes de l'instance et de l'élément
     * <p>
     * Le test doit être commutatif:
     * <code>
     * a.collisionne(b) == b.collisionne(a)
     * </code>
     *
     * @param element L'élément à tester
     *
     * @return {@code true} s'il y a collision sinon {@code false}
     *
     * @throws NoIntersectionException Quand on ne sait pas déterminer l'existance de points de collision
     */
    default boolean collisionne(ICollisionable element) throws NoIntersectionException {
        Forme thisForme = this.getForme();
        Forme elementForme = element.getForme();
        return thisForme.collisionne(elementForme); // Collisione la forme des éléments
    }

    /**
     * Gère la collision avec l'élèment
     *
     * @param element L'élèment avec lequel on collisionne
     */
    void gereCollision(ICollisionable element);
}
