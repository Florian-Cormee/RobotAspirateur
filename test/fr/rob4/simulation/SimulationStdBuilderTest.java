package fr.rob4.simulation;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.strategie.StrategieAleatoire;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SimulationStdBuilderTest {

    @Test
    void ajouteElement() {
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        try {
            ssb.ajouteElement(null);
            Assertions.fail();
        } catch (NullPointerException ignored) {
        }
        ICollisionable obstacle = ElementFactory.getInstance().obstacle(new Cercle(0, 0, 0.5));
        ssb.ajouteElement(obstacle);
        Simulation simulation = ssb.build();
        Assertions.assertEquals(2, simulation.getElements().size()); // La simulation contient les murs aussi
        Assertions.assertEquals(obstacle, simulation.getElements(ICollisionable.class).get(0));
    }

    @Test
    void ajouteObstacleCirculaire() {
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ssb.ajouteObstacleCirculaire(3, 0, 1));
        ssb.ajouteObstacleCirculaire(0, 0, 1);
        Simulation simulation = ssb.build();
        Assertions.assertEquals(2, simulation.getElements().size()); // La simulation contient les murs aussi
        ICollisionable obstacle = ElementFactory.getInstance().obstacle(new Cercle(0, 0, 0.5));
        Assertions.assertEquals(obstacle, simulation.getElements(ICollisionable.class).get(0));
    }

    @Test
    void ajouteRobotStandard() {
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> ssb.ajouteRobotStandard(42, 0, 0, new StrategieAleatoire()));
        Assertions.assertThrows(NullPointerException.class, () -> ssb.ajouteRobotStandard(0, 0, 0, null));
        ssb.ajouteRobotStandard(0, 0, 0, new StrategieAleatoire());
        Simulation simulation = ssb.build();
        List<IRobot> robots = simulation.getElements(IRobot.class);
        // Test le nombre de robots
        Assertions.assertEquals(1, robots.size());
        IRobot r = robots.get(0);
        // Test la quantité de capteurs
        Assertions.assertEquals(3, r.getModules(CapteurContact.class).size());
        Assertions.assertEquals(1, r.getModules(CapteurSalete.class).size());
        Assertions.assertEquals(4, r.getModules().size());
    }

    @Test
    void ajouteTacheCirculaire() {
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        try {
            ssb.ajouteTacheCirculaire(3, 0, 1); // La tache est hors zone
            Assertions.fail();
        } catch (IllegalArgumentException ignored) {
        }
        try {
            ssb.ajouteTacheCirculaire(0, 0, 1); // La tache est plus grande que le robot
            Assertions.fail();
        } catch (IllegalArgumentException ignored) {
        }
        ssb.ajouteTacheCirculaire(0, 0, SimulationStdBuilder.DIAMETRE_ROBOT_STD - 0.01);
        Simulation simulation = ssb.build();
        Assertions.assertEquals(2, simulation.getElements().size()); // La simulation contient les murs aussi
        INettoyable tache = ElementFactory.getInstance().tache(new Cercle(0,
                                                                          0,
                                                                          (SimulationStdBuilder.DIAMETRE_ROBOT_STD -
                                                                           0.01) / 2));
        Assertions.assertEquals(tache, simulation.getElements(INettoyable.class).get(0));
    }

    @Test
    void build() {
        SimulationStdBuilder ssb = new SimulationStdBuilder(ElementFactory.getInstance());
        Simulation simulation = ssb.build();
        Assertions.assertEquals(1, simulation.getElements().size()); // Ne contient que les murs
        Obstacle obstacle = simulation.getElements(Obstacle.class).get(0);
        Forme forme = obstacle.getForme();
        Assertions.assertTrue(forme instanceof Rectangle);
        Rectangle rectangle = (Rectangle) forme;
        Assertions.assertEquals(4, rectangle.getLargeur());
        Assertions.assertEquals(4, rectangle.getHauteur());
        Assertions.assertEquals(0, rectangle.getCentre().getPositionAbsolue().getX());
        Assertions.assertEquals(0, rectangle.getCentre().getPositionAbsolue().getY());
        // Les test de ajoutes* complètent ce test
    }

    @Test
    void testEquals() {
        SimulationStdBuilder ssb1 = new SimulationStdBuilder(ElementFactory.getInstance());
        SimulationStdBuilder ssb2 = ssb1;
        // Tests sur les références
        Assertions.assertEquals(ssb1, ssb2);
        ssb2 = null;
        Assertions.assertNotEquals(ssb1, ssb2);
        // Tests sur les contenus
        ssb2 = new SimulationStdBuilder(ElementFactory.getInstance());
        Assertions.assertEquals(ssb1, ssb2);
        ssb2.ajouteObstacleCirculaire(0, 0, 1);
        ssb1.ajouteObstacleCirculaire(0, 0, 1);
        Assertions.assertEquals(ssb1, ssb2);
        ssb1.ajouteTacheCirculaire(1, 0, 0.1);
        Assertions.assertNotEquals(ssb1, ssb2);
    }
}