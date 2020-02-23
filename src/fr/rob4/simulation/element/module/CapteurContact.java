package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.geometrie.Forme;

import java.util.List;
import java.util.stream.Stream;

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
        Stream<ICollisionable> stream = elements.stream();
        stream = stream.filter(e -> !(e instanceof Robot));
        collision = stream.anyMatch(e -> forme.estSupperposee(e.getForme()));
    }

    @Override
    public Boolean getInfo() {
        return collision;
    }

}
