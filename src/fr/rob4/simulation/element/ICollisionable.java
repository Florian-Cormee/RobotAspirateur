package fr.rob4.simulation.element;

import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

public interface ICollisionable extends IElement {
    /**
     * Pr�dicat d'une collision
     * <p>
     * Test la superposition des formes de l'instance et de l'�l�ment
     * </p>
     * <p>
     * Le test doit �tre commutatif:
     * <code>
     * a.collisionne(b) == b.collisionne(a)
     * </code>
     * </p>
     *
     * @param element L'�l�ment a tester
     *
     * @return {@code true} s'il y a collision sinon {@code false}
     * @throws NoIntersectionException Quand on ne sait pas déterminer l'existance de points de collision
     */
    default boolean collisionne(ICollisionable element) throws NoIntersectionException {
        Forme thisForme = getForme();
        Forme elementForme = element.getForme();
        return thisForme.collisionne(elementForme);
    }

    /**
     * G�re la collision avec l'�l�ment
     *
     * @param element L'�l�ment avec lequel on collisionne
     */
    void gereCollision(ICollisionable element);
}
