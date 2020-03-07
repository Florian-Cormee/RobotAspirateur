package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

import java.util.List;

public class CapteurContact extends Element implements IModule<Boolean> {
    protected ICollisionable element;

    public CapteurContact(Forme forme) {
        super(forme);
        this.element = null;
    }

    @Override
    public void actualise(Simulation simulation, Object appeleur) {
        List<ICollisionable> elements = simulation.getElements(ICollisionable.class);
        // Cherche parmis les éléments collisionable un avec lequel l'instance
        // se superpose
        this.element = null;
        for (ICollisionable element : elements) {
            if (element.equals(appeleur)) {
                continue;
            }
            try {
                Forme forme = element.getForme();
                if (this.forme.collisionne(forme)) {
                    this.element = element;
                }

            } catch (NoIntersectionException ignored) {
            }
            if (this.element != null) {
                break;
            }
        }
    }

    @Override
    public Boolean getInfo() {
        return this.element != null;
    }

}
