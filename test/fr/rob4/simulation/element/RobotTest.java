package fr.rob4.simulation.element;

import fr.rob4.simulation.ElementFactory;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.SimulationStdBuilder;
import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.strategie.StrategieAleatoire;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RobotTest {

    @Test
    void actualise() {
        // Crée une simulation simple
        SimulationStdBuilder builder = new SimulationStdBuilder(ElementFactory.getInstance());
        builder.ajouteRobotStandard(0, 0, 0, new StrategieAleatoire());
        builder.ajouteTacheCirculaire(0.05, 0, 0.1);
        builder.ajouteObstacleCirculaire(SimulationStdBuilder.DIAMETRE_ROBOT_STD / 2 + 0.35, 0, 1.02);
        Simulation simulation = builder.build();
        Robot robot = simulation.getElements(Robot.class).get(0); // Récupère le robot
        robot.actualise(simulation, robot);
        CapteurSalete capteurSalete = robot.getModules(CapteurSalete.class).get(0);
        Assertions.assertTrue(capteurSalete.getInfo());
        List<CapteurContact> capteurContacts = robot.getModules(CapteurContact.class);
        Assertions.assertTrue(capteurContacts.get(0).getInfo());
        Assertions.assertFalse(capteurContacts.get(1).getInfo());
        Assertions.assertTrue(capteurContacts.get(2).getInfo());
    }

    @Test
    void deplace() {
    }

    @Test
    void gereCollision() {
    }

    @Test
    void getModules() {
    }

    @Test
    void rotation() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void translation() {
    }
}