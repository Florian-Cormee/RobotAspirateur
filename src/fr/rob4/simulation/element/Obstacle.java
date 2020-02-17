package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.geometrie.Forme;

public class Obstacle extends Element implements ICollisionable {

    public Obstacle(Forme forme) {
	super(forme);
    }

    @Override
    public boolean gereCollision(Element element) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean actualise(Simulation simulation) {
	// TODO Auto-generated method stub
	return false;
    }
}
