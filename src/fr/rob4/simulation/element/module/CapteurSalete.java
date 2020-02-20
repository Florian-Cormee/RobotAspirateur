package fr.rob4.simulation.element.module;

import java.util.List;
import java.util.stream.Stream;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.geometrie.Forme;

public class CapteurSalete extends Element implements IModule<Boolean> {
    protected boolean sale;

    public CapteurSalete(Forme forme) {
	super(forme);
	sale = false;
    }

    @Override
    public boolean actualise(Simulation simulation) {
	List<INettoyable> nettoyables = simulation
		.getElements(INettoyable.class);
	Stream<INettoyable> stream = nettoyables.stream();
	sale = stream.anyMatch(e -> forme.estSupperposee(e.getForme()));
	return true;
    }

    @Override
    public Boolean getInfo() {
	return sale;
    }

}
