package fr.rob4.simulation.element;

import fr.rob4.simulation.ElementFactory;
import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.strategie.IStrategie;

import java.util.Arrays;
import java.util.List;

public class BasiqueElementFactory extends ElementFactory {
    @Override
    public IModule<Boolean> capteurContact(Forme forme) {
        return new CapteurContact(forme);
    }

    @Override
    public IModule<Boolean> capteurSalete(Forme forme) {
        return new CapteurSalete(forme);
    }

    @Override
    public ICollisionable obstacle(Forme forme) {
        return new Obstacle(forme);
    }

    @Override
    public IRobot robot(Forme forme,
                        IStrategie strategie,
                        double orientation,
                        double ecartRoues,
                        IModule<?>... modules) {
        List<IModule<?>> listModules = Arrays.asList(modules);
        return new Robot(forme, listModules, strategie, orientation, ecartRoues);
    }

    @Override
    public INettoyable tache(Forme forme) {
        return new Tache(forme);
    }
}
