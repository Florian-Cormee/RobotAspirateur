package fr.rob4.simulation;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.element.Tache;
import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.strategie.StrategieAleatoire;
import fr.rob4.simulation.vue.SimulationPanel;
import fr.rob4.simulation.vue.element.NettoyableDessinable;
import fr.rob4.simulation.vue.element.ObstacleDessinable;
import fr.rob4.simulation.vue.element.RobotDessinable;
import fr.rob4.simulation.vue.element.module.CapteurDessinable;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
	ICollisionable collisionable = new Obstacle(new Rectangle(0, 0, 4, 4));
	ICollisionable bordures = new ObstacleDessinable(collisionable,
		Color.GRAY);

        INettoyable tache = new Tache(new Cercle(1.9, 1.9, 0.1));
	INettoyable tacheD = new NettoyableDessinable(tache, Color.DARK_GRAY);
	
	IModule<Boolean> cs = new CapteurSalete(new Cercle(0, 0, 0.025));
	IModule<Boolean> csD = new CapteurDessinable<Boolean>(cs, true);

	IModule<Boolean> cc = new CapteurContact(
		new ArcDeCercle(0, 0, 0.17, Math.PI / 3, 2 * Math.PI / 3));
	IModule<Boolean> ccD = new CapteurDessinable<Boolean>(cc, false);
	
	IRobot robot = new Robot(new Cercle(0, 0, 0.17), Arrays.asList(csD,ccD),
		new StrategieAleatoire(), Math.PI / 3, 1);
	IRobot robotD = new RobotDessinable(robot);
	
	Simulation s = new Simulation(bordures, Arrays.asList(tacheD, robotD));
	SimulationPanel dessinateur = new SimulationPanel(s);

	JFrame frame = new JFrame("Simulation");
	frame.add(dessinateur);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.setSize(250, 250);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}
