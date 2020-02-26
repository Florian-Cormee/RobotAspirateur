package fr.rob4.simulation;

import fr.rob4.simulation.element.*;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.vue.SimulationPanel;
import fr.rob4.simulation.vue.element.NettoyableDessinable;
import fr.rob4.simulation.vue.element.ObstacleDessinable;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        ICollisionable collisionable = new Obstacle(new Rectangle(0, 0, 4, 4));
        ICollisionable bordures = new ObstacleDessinable(collisionable, Color.GRAY);

        INettoyable tache = new Tache(new Cercle(1.9, 1.9, 0.2));
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
