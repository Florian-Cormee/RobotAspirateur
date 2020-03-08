package fr.rob4.simulation;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Polygone;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.StrategieAleatoire;
import fr.rob4.simulation.vue.SimulationPanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.Arrays;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ElementFactory factory = ElementFactory.getInstance();
        SimulationStdBuilder builder = new SimulationStdBuilder(factory);
        builder.ajouteObstacleCirculaire(1, 1, 1);
        builder.ajouteObstacleCirculaire(-1, 1, 1);
        builder.ajouteObstacleCirculaire(1, -1, 1);
        builder.ajouteObstacleCirculaire(-1, -1, 1);
        builder.ajouteTacheCirculaire(0, 1, 0.3);
        builder.ajouteTacheCirculaire(0, -1, 0.2);
        builder.ajouteTacheCirculaire(1, 0, 0.3);
        builder.ajouteElement(factory.tache(new Rectangle(-1, 0, 0.25, 0.15)));
        Polygone polygone = new Polygone(Arrays.asList(new Point2D(new Vecteur2D(0, 0)),
                                                       new Point2D(new Vecteur2D(0.4, 0.2)),
                                                       new Point2D(new Vecteur2D(0.3, 0)),
                                                       new Point2D(new Vecteur2D(0.4, -0.2))));
        builder.ajouteElement(factory.obstacle(polygone));
        builder.ajouteRobotStandard(0, -1, 0, new StrategieAleatoire());
        Simulation s = builder.build();
        SimulationPanel dessinateur = new SimulationPanel(s);

        JFrame frame = new JFrame("Simulation");
        frame.add(dessinateur);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1080, 1080);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            s.lancer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
