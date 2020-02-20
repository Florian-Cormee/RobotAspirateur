package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;

public class Obstacle extends Element implements ICollisionable {

    public Obstacle(Forme forme) {
	super(forme);
    }

    @Override
    public boolean gereCollision(Element element) {
	// Ne rien faire, l'obstacle est lié au bâti
	return false;
    }
}
