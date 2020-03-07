package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Segment;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.element.module.CapteurDessinable;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.Objects;

public class RobotDessinable implements IRobot, IDessinable {
    private IRobot robot;

    public RobotDessinable(IRobot robot) {
        this.robot = Objects.requireNonNull(robot);
    }

    @Override
    public void actualise(Simulation simulation) {
        this.robot.actualise(simulation);
    }

    @Override
    public void deplace(double dG, double dD) {
        this.robot.deplace(dG, dD);
    }

    @Override
    public List<IModule<?>> getModules() {
        return this.robot.getModules();
    }

    @Override
    public <T extends IModule<?>> List<T> getModules(Class<? extends T> c) {
        return this.robot.getModules(c);
    }

    @Override
    public IStrategie getStrategie() {
        return this.robot.getStrategie();
    }

    @Override
    public boolean isNettoie() {
        return this.robot.isNettoie();
    }

    @Override
    public void setNettoie(boolean etat) {
        this.robot.setNettoie(etat);
    }

    @Override
    public Point2D getPosition() {
        return this.robot.getPosition();
    }

    @Override
    public double getOrientation() {
        return this.robot.getOrientation();
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precColor = graphics2D.getColor();
        Stroke precStroke = graphics2D.getStroke();

        graphics2D.setColor(Color.PINK);
        Forme forme = this.robot.getForme();
        IDessinateur<Forme> formeDess = GeometrieDessinateurFactory.instance.forme();
        formeDess.dessine(graphics2D, echelle, true, forme);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(2));
        double orientation = this.robot.getOrientation();
        Rectangle dim = forme.getDimension();
        Point2D centre = forme.getCentre();
        double longueur = Math.min(dim.getHauteur(), dim.getLargeur()) / 2;
        Point2D extremite = new Point2D(centre, new Vecteur2D(longueur, 0));
        extremite = extremite.rotationOrigine(orientation);
        Segment direction = new Segment(centre, extremite);
        IDessinateur<Segment> segDess = GeometrieDessinateurFactory.instance.segment();
        segDess.dessine(graphics2D, echelle, false, direction);

        graphics2D.setColor(precColor);
        graphics2D.setStroke(precStroke);

        for (IModule<?> module : this.robot.getModules()) {
            if (module instanceof IDessinable) {
                ((IDessinable) module).dessine(graphics2D, echelle);
            }else{
                new CapteurDessinable<>(module, false);
            }
        }
    }

    @Override
    public void gereCollision(ICollisionable element) {
        this.robot.gereCollision(element);
    }

    @Override
    public Forme getForme() {
        return this.robot.getForme();
    }
}
