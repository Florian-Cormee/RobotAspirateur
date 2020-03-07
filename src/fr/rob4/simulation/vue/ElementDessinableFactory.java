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
import fr.rob4.simulation.vue.element.module.CapteurDessinable;

import java.awt.Color;
import java.util.Objects;

public class ElementDessinableFactory extends ElementFactory {
    private ElementFactory factoryBasique;

    public ElementDessinableFactory(ElementFactory factoryBasique) {
        this.factoryBasique = Objects.requireNonNull(factoryBasique);
    }

    @Override
    public IModule<Boolean> capteurContact(Forme forme) {
        IModule<Boolean> capteurContact = this.factoryBasique.capteurContact(forme);
        return new CapteurDessinable<>(capteurContact, false);
    }

    @Override
    public IModule<Boolean> capteurSalete(Forme forme) {
        IModule<Boolean> capteurSalete = this.factoryBasique.capteurSalete(forme);
        return new CapteurDessinable<>(capteurSalete, true);
    }

    @Override
    public ICollisionable obstacle(Forme forme) {
        ICollisionable obstacle = this.factoryBasique.obstacle(forme);
        return new ObstacleDessinable(obstacle, Color.BLACK);
    }

    @Override
    public IRobot robot(Forme forme,
                        IStrategie strategie,
                        double orientation,
                        double ecartRoues,
                        IModule<?>... modules) {
        IRobot robot = this.factoryBasique.robot(forme, strategie, orientation, ecartRoues, modules);
        return new RobotDessinable(robot);
    }

    @Override
    public INettoyable tache(Forme forme) {
        INettoyable tache = this.factoryBasique.tache(forme);
        return new NettoyableDessinable(tache, Color.GRAY);
    }
}
