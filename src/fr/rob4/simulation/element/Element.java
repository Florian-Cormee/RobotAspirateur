package fr.rob4.simulation.element;

import java.util.Objects;

import fr.rob4.simulation.geometrie.Forme;

public abstract class Element implements IElement {
    protected Forme forme;

    public Element(Forme forme) {
	this.forme = Objects.requireNonNull(forme);
    }
    
    public Forme getForme() {
	return forme;
    }
}
