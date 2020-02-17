package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.geometrie.Forme;

public class CapteurSalete extends Element implements IModule<Boolean>{

    public CapteurSalete(Forme forme) {
	super(forme);
    }

    @Override
    public boolean actualise(Simulation simulation) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public Boolean getInfo() {
	// TODO Auto-generated method stub
	return null;
    }

}
