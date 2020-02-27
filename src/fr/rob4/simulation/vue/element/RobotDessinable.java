package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.strategie.IStrategie;
import fr.rob4.simulation.vue.IDessinable;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class RobotDessinable implements IRobot, IDessinable {
    private IRobot robot;

    public RobotDessinable(IRobot robot) {
        this.robot = Objects.requireNonNull(robot);
    }

    @Override
    public void deplace(double dG, double dD) {
        robot.deplace(dG, dD);
    }

    @Override
    public List<IModule<?>> getModules() {
        return robot.getModules();
    }

    @Override
    public <T extends IModule<?>> List<T> getModules(Class<T> c) {
        return robot.getModules(c);
    }

    @Override
    public IStrategie getStrategie() {
        return robot.getStrategie();
    }

    @Override
    public boolean isNettoie() {
        return robot.isNettoie();
    }

    @Override
    public void setNettoie(boolean etat) {
        robot.setNettoie(etat);
    }

    @Override
    public void actualise(Simulation simulation) {
        robot.actualise(simulation);
    }

    @Override
    public void gereCollision(ICollisionable element) {
        robot.gereCollision(element);
    }

    @Override
    public Forme getForme() {
        return robot.getForme();
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
    // TODO
    }
}
