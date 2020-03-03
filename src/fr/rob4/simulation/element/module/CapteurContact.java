package fr.rob4.simulation.element.module;

import java.util.List;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

public class CapteurContact extends Element implements IModule<Boolean> {
    protected boolean collision;

    public CapteurContact(Forme forme) {
	super(forme);
	collision = false;
    }

    @Override
    public void actualise(Simulation simulation) {
	List<ICollisionable> elements = simulation.getElements(ICollisionable.class);
	// Cherche parmis les éléments collisionable un avec lequel l'instance
	// se superpose
	collision = false;
	for (ICollisionable element : elements) {
	    try {
		collision = forme.estSuperposee(element.getForme());

	    } catch (NoIntersectionException e) {
	    }
	    if (collision) {
		break;
	    }
	}
    }

    @Override
    public Boolean getInfo() {
	return collision;
    }

}
