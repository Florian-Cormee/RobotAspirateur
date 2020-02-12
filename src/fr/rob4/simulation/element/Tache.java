package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.geometrie.Forme;

public class Tache extends Element implements INettoyable {

    public Tache(Forme forme) {
	super(forme);
    }

    @Override
    public boolean actualise(Simulation simulation) {
	// TODO Auto-generated method stub
	return false;
    }

}
