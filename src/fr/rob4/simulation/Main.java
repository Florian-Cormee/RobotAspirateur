package fr.rob4.simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IElement;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.element.Tache;
import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.vue.SimulationPanel;
import fr.rob4.simulation.vue.element.NettoyableDessinable;
import fr.rob4.simulation.vue.element.ObstacleDessinable;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ICollisionable collisionable = new Obstacle(new Rectangle(0, 0, 4, 4));
        ICollisionable bordures = new ObstacleDessinable(collisionable, Color.GRAY);

//        INettoyable tache = new Tache(new Cercle(1.9, 1.9, 0.2));
        INettoyable tache = new Tache(new ArcDeCercle(1, 0, 1, Math.PI/3, 3*Math.PI/2));
//        Point2D p1 = new Point2D(new Vecteur2D());
//        Point2D p2 = new Point2D(p1, new Vecteur2D(1, 0));
//        Point2D p3 = new Point2D(p1, new Vecteur2D(0, 1));
//        Point2D p4 = new Point2D(p1, new Vecteur2D(1.5, 2));
//        List<Point2D> points = Arrays.asList(p1, p2, p4,p3);
//        Polygone polygone = new Polygone(p1, points);
//        INettoyable tache = new Tache(polygone);
        INettoyable tacheD = new NettoyableDessinable(tache, Color.DARK_GRAY);

        List<IElement> elems = new ArrayList<>();
        elems.add(tacheD);
        Simulation s = new Simulation(bordures, elems);
        SimulationPanel dessinateur = new SimulationPanel(s);

        JFrame frame = new JFrame("Simulation");
        frame.add(dessinateur);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
