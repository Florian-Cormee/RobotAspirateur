package fr.rob4.simulation.vue;

import fr.rob4.simulation.ElementFactory;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.strategie.IStrategie;
import fr.rob4.simulation.vue.element.NettoyableDessinable;
import fr.rob4.simulation.vue.element.ObstacleDessinable;
import fr.rob4.simulation.vue.element.RobotDessinable;
import fr.rob4.simulation.vue.element.module.CapteurContactDessinable;
import fr.rob4.simulation.vue.element.module.CapteurSaleteDessinable;

import java.awt.Color;
import java.util.Arrays;

public class ElementDessinableFactory extends ElementFactory {

    public ElementDessinableFactory() {
    }

    @Override
    public IModule<Boolean> capteurContact(Forme forme) {
        return new CapteurContactDessinable(forme);
    }

    @Override
    public IModule<Boolean> capteurSalete(Forme forme) {
        return new CapteurSaleteDessinable(forme);
    }

    @Override
    public ICollisionable obstacle(Forme forme) {
        return new ObstacleDessinable(forme, Color.BLACK);
    }

    @Override
    public IRobot robot(Forme forme,
                        IStrategie strategie,
                        double orientation,
                        double ecartRoues,
                        IModule<?>... modules) {
        return new RobotDessinable(forme, Arrays.asList(modules), strategie, orientation, ecartRoues);
    }

    @Override
    public INettoyable tache(Forme forme) {
        return new NettoyableDessinable(forme, Color.GRAY);
    }
}
