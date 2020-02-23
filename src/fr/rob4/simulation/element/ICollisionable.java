package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;

public interface ICollisionable extends IElement {
    /**
     * Prédicat d'une collision
     * <p>
     * Test la superposition des formes de l'instance et de l'élément
     * </p>
     * <p>
     * Le test doit être commutatif:
     * <code>
     * a.collisionne(b) == b.collisionne(a)
     * </code>
     * </p>
     *
     * @param element L'élément a tester
     *
     * @return {@code true} s'il y a collision sinon {@code false}
     */
    default boolean collisionne(ICollisionable element) {
        Forme thisForme = getForme();
        Forme elementForme = element.getForme();
        return thisForme.estSupperposee(elementForme);
    }

    /**
     * Gère la collision avec l'élément
     *
     * @param element L'élément avec lequel on collisionne
     */
    void gereCollision(ICollisionable element);
}
