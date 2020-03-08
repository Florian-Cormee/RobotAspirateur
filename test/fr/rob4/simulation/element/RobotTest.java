package fr.rob4.simulation.element;

import fr.rob4.simulation.ElementFactory;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.SimulationStdBuilder;
import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.StrategieAleatoire;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class RobotTest {

    @Test
    void actualise() {
        // Crée une simulation simple
        SimulationStdBuilder builder = new SimulationStdBuilder(ElementFactory.getInstance());
        builder.ajouteRobotStandard(0, 0, 0, new StrategieAleatoire());
        builder.ajouteTacheCirculaire(0.05, 0, 0.1);
        builder.ajouteObstacleCirculaire(SimulationStdBuilder.DIAMETRE_ROBOT_STD,
                                         0,
                                         SimulationStdBuilder.DIAMETRE_ROBOT_STD);
        Simulation simulation = builder.build();
        Robot robot = simulation.getElements(Robot.class).get(0); // Récupère le robot
        Point2D position = robot.getPosition();
        robot.actualise(simulation, robot);
        // Le capteur a détecté une tache
        CapteurSalete capteurSalete = robot.getModules(CapteurSalete.class).get(0);
        Assertions.assertTrue(capteurSalete.getInfo());
        // Les capteurs ont détecté une collision
        List<CapteurContact> capteurContacts = robot.getModules(CapteurContact.class);
        Assertions.assertFalse(capteurContacts.get(0).getInfo());// Capteur à gauche
        Assertions.assertTrue(capteurContacts.get(1).getInfo()); // Capteur central
        Assertions.assertFalse(capteurContacts.get(2).getInfo()); // Capteur à droite
        Assertions.assertNotEquals(position, robot.getPosition()); // La stratégie a déplacé le robot
    }

    @Test
    void deplace() {
        Robot robot = new Robot(new Cercle(0, 0, 0.1), Collections.emptyList(), new StrategieAleatoire(), 0, 0.2);
        double orientationInit = robot.getOrientation();
        Point2D positionInit = robot.getPosition();
        robot.deplace(5, 5); // Déplacement rectiligne plus rapide que ce qui est autorisé
        Assertions.assertEquals(positionInit.deplace(new Vecteur2D(0.01, 0)), robot.getPosition());
        Assertions.assertEquals(orientationInit, robot.getOrientation());
        positionInit = robot.getPosition();
        robot.deplace(-1, 1); // Rotation sur soi-même
        Assertions.assertEquals(positionInit, robot.getPosition());
        Assertions.assertEquals(orientationInit + Math.atan2(0.01, 0.1), robot.getOrientation(), 1e-3);
    }

    @Test
    void gereCollision() {
    }

    @Test
    void getModules() {
        // Création d'un robot
        CapteurContact cc1 = new CapteurContact(new ArcDeCercle(0, 0, 0.1, 0.1, 0));
        CapteurContact cc2 = new CapteurContact(new ArcDeCercle(0, 0, 0.1, 0, -0.1));
        CapteurSalete cs = new CapteurSalete(new Rectangle(0, 0, 0.05, 0.05));
        List<IModule<?>> modules = Arrays.asList(cc1, cc2, cs);
        Robot r = new Robot(new Cercle(0, 0, 0.2), modules, new StrategieAleatoire(), 0, 0.4);
        // Obtention des capteurs par type
        List<CapteurSalete> capteurSaletes = r.getModules(CapteurSalete.class);
        Assertions.assertEquals(1, capteurSaletes.size());
        Assertions.assertSame(cs, capteurSaletes.get(0));
        // Ordre conservé
        List<CapteurContact> capteurContacts = r.getModules(CapteurContact.class);
        Assertions.assertEquals(2, capteurContacts.size());
        Assertions.assertSame(cc1, capteurContacts.get(0));
        Assertions.assertSame(cc2, capteurContacts.get(1));
        // Obtention de tous les capteurs
        List<IModule<?>> moduleList = r.getModules();
        Assertions.assertEquals(3, moduleList.size());
        Assertions.assertTrue(moduleList.containsAll(modules));
    }

    @Test
    void rotation() {
        // Création d'un robot
        Forme fcc = new ArcDeCercle(0, 0, 0.1, 0.1, 0);
        CapteurContact cc = new CapteurContact(fcc);
        Forme fcs = new Rectangle(0, 0, 0.05, 0.05);
        CapteurSalete cs = new CapteurSalete(fcs);
        List<IModule<?>> modules = Arrays.asList(cc, cs);
        Forme fr = new Cercle(0, 0, 0.2);
        Robot r = new Robot(fr, modules, new StrategieAleatoire(), 0, 0.4);

        Point2D centre = new Point2D(new Vecteur2D());
        double angle = 0.1234;
        r.rotation(angle, centre);
        // S'assure que le robot et ses capteurs aient fait la rotation
        Assertions.assertEquals(fr.rotation(angle, centre), r.getForme());
        Assertions.assertEquals(fcs.rotation(angle, centre), cs.getForme());
        Assertions.assertEquals(fcc.rotation(angle, centre), cc.getForme());
    }

    @Test
    void testEquals() {
        // Création d'un robot
        CapteurContact cc1 = new CapteurContact(new ArcDeCercle(0, 0, 0.1, 0.1, 0));
        CapteurSalete cs1 = new CapteurSalete(new Rectangle(0, 0, 0.05, 0.05));
        List<IModule<?>> modules = Arrays.asList(cc1, cs1);
        Forme fr = new Cercle(0, 0, 0.2);
        Robot r1 = new Robot(fr, modules, new StrategieAleatoire(), 0, 0.4);
        // Test des références
        Robot r2 = r1;
        Assertions.assertEquals(r1, r2);
        r2 = null;
        Assertions.assertNotEquals(r1, r2);
        // Tests des éléments
        r2 = new Robot(fr, modules, new StrategieAleatoire(), 0, 0.4);
        Assertions.assertEquals(r1, r2);
        CapteurSalete cs2 = new CapteurSalete(new Rectangle(0, 0, 0.05, 0.05));
        List<IModule<?>> modules2 = Arrays.asList(cc1, cs2);
        r2 = new Robot(fr, modules2, new StrategieAleatoire(), 0, 0.4);
        Assertions.assertEquals(r1, r2);
        CapteurContact cc2 = new CapteurContact(new ArcDeCercle(0, 0, 0.2, 0.1, 0));
        List<IModule<?>> modules3 = Arrays.asList(cc2, cs2);
        r2 = new Robot(fr, modules3, new StrategieAleatoire(), 0, 0.4);
        Assertions.assertNotEquals(r1, r2);
    }

    @Test
    void translation() {
        // Création d'un robot
        Forme fcc = new ArcDeCercle(0, 0, 0.1, 0.1, 0);
        CapteurContact cc = new CapteurContact(fcc);
        Forme fcs = new Rectangle(0, 0, 0.05, 0.05);
        CapteurSalete cs = new CapteurSalete(fcs);
        List<IModule<?>> modules = Arrays.asList(cc, cs);
        Forme fr = new Cercle(0, 0, 0.2);
        Robot r = new Robot(fr, modules, new StrategieAleatoire(), 0, 0.4);

        Vecteur2D deplacement = new Vecteur2D(-0.2468, 42);
        r.translation(deplacement);
        // S'assure que le robot et ses capteurs aient fait la translation
        Assertions.assertEquals(fr.deplace(deplacement), r.getForme());
        Assertions.assertEquals(fcs.deplace(deplacement), cs.getForme());
        Assertions.assertEquals(fcc.deplace(deplacement), cc.getForme());
    }
}