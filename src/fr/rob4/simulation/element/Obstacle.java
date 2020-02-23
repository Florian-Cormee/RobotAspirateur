package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;

public class Obstacle extends Element implements ICollisionable {

    public Obstacle(Forme forme) {
        super(forme);
    }

    @Override
    public void gereCollision(ICollisionable element) {
        // Ne rien faire, l'obstacle est lié au bâti
    }
}
