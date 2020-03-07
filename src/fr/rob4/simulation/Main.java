package fr.rob4.simulation;

import fr.rob4.simulation.strategie.StrategieAleatoire;
import fr.rob4.simulation.vue.SimulationPanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ElementFactory factory = ElementFactory.getInstance();
        SimulationStdBuilder builder = new SimulationStdBuilder(factory);
		builder.ajouteRobotStandard(0, 0, 0, new StrategieAleatoire());
		Simulation s = builder.build();
        SimulationPanel dessinateur = new SimulationPanel(s);

        JFrame frame = new JFrame("Simulation");
        frame.add(dessinateur);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
