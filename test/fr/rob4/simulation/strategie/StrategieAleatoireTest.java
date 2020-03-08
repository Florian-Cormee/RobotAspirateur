package fr.rob4.simulation.strategie;

import fr.rob4.simulation.ElementFactory;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.SimulationStdBuilder;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.CapteurContact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StrategieAleatoireTest {

    @Test
    void decide() {
        StrategieAleatoire sa = new StrategieAleatoire();
        Assertions.assertSame(StrategieAleatoire.Etats.AVANCE, sa.getEtat()); // Test l'état initial
        // Création d'une simulation
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        ssb.ajouteRobotStandard(0, 0, 0, sa);
        ssb.ajouteObstacleCirculaire(0.18, 0, 0.19); // Force la collision
        Simulation simulation = ssb.build();
        IRobot robot = simulation.getElements(IRobot.class).get(0);
        CapteurContact capteurContact = robot.getModules(CapteurContact.class).get(0);
        capteurContact.actualise(simulation, robot);
        sa.decide(robot); // Changement d'état par collision
        Assertions.assertSame(StrategieAleatoire.Etats.RECUL, sa.getEtat());
        // Marche arrière sur 5 cm
        for (int i = 0 ; i < 5 ; i++) {
            capteurContact.actualise(simulation, robot);
            sa.decide(robot);
        }
        Assertions.assertSame(StrategieAleatoire.Etats.TOURNE, sa.getEtat());
        // Rotation vers un angle aleatoire avec une précision de 10^-3 radian
        while (Math.abs(robot.getOrientation() - sa.getAngleCible()) > 1e-3) {
            sa.decide(robot);
        }
        Assertions.assertSame(StrategieAleatoire.Etats.AVANCE, sa.getEtat());
    }

    @Test
    void testEquals() {
        StrategieAleatoire sa = new StrategieAleatoire();
        StrategieAleatoire sa2 = sa;
        // Tests sur les références
        Assertions.assertEquals(sa, sa2);
        sa2 = null;
        Assertions.assertNotEquals(sa, sa2);
        // Tests sur les valeurs
        sa2 = new StrategieAleatoire();
        Assertions.assertEquals(sa, sa2);
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        ssb.ajouteRobotStandard(0, 0, 0, sa2);
        ssb.ajouteObstacleCirculaire(0.17, 0, 0.36);
        Simulation simulation = ssb.build();
        IRobot robot = simulation.getElements(IRobot.class).get(0);
        CapteurContact capteurContact = robot.getModules(CapteurContact.class).get(0);
        capteurContact.actualise(simulation, robot);
        sa2.decide(robot); // Changement d'état par collision
        Assertions.assertNotEquals(sa, sa2);
    }
}