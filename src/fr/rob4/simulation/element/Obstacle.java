package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;

public class Obstacle extends Element implements ICollisionable {

    /**
     * Crée un obstacle à partir de sa forme.
     *
     * @param forme La forme ne peut pas être <code>null</code>
     */
    public Obstacle(Forme forme) {
        super(forme);
    }

    @Override
    public void gereCollision(ICollisionable element) {
        // Ne rien faire, l'obstacle est lié au bâti
    }

    @Override
    public String toString() {
        return "Obstacle[toString=" + super.toString() + "]";
    }
}
