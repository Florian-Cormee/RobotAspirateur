package fr.rob4.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.rob4.simulation.element.IElement;

public class Simulation {
    /** Période d'échantillonage (en secondes) */
    public static final double T = 1e-2;
    
    protected List<IElement> elements;

    public List<IElement> getElements() {
	return Collections.unmodifiableList(elements);
    }

    public <T extends IElement> List<T> getElements(Class<T> c) {
	List<T> list = new ArrayList<>();
	for (IElement element : elements) {
	    if (c.isInstance(element)) {
		list.add(c.cast(element));
	    }
	}
	return list;
    }

    public void joue() {
	// TODO
    }

    public void pause() {
	// TODO
    }

    public void quitter() {
	// TODO
    }

    protected void actualise() {
	// TODO
    }
}
