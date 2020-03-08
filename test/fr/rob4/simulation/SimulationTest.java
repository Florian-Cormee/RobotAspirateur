package fr.rob4.simulation;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.geometrie.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

class SimulationTest {

    @Test
    void ajouteObservateur() {
        ICollisionable bordures = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        Simulation s = new Simulation(bordures, Collections.emptyList());
        IObservateur<Simulation> obs = simu -> {};
        s.ajouteObservateur(obs);
        Assertions.assertThrows(NullPointerException.class, () -> s.ajouteObservateur(null));
        Assertions.assertTrue(s.estObserve(obs));
        Assertions.assertFalse(s.estObserve(null));
    }

    @Test
    void estObserve() {
        ICollisionable bordures = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        Simulation s = new Simulation(bordures, Collections.emptyList());
        IObservateur<Simulation> obs1 = simu -> {};
        Assertions.assertFalse(s.estObserve(obs1));
        s.ajouteObservateur(obs1);
        Assertions.assertTrue(s.estObserve(obs1));
    }

    @Test
    void notifierTous() {
        ICollisionable bordures = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        Simulation s = new Simulation(bordures, Collections.emptyList());
        AtomicBoolean notifie1 = new AtomicBoolean(false);
        AtomicBoolean notifie2 = new AtomicBoolean(false);
        IObservateur<Simulation> obs1 = simu -> notifie1.set(true);
        IObservateur<Simulation> obs2 = simu -> notifie2.set(true);
        // Ajout d'un seul observateur
        s.ajouteObservateur(obs1);
        s.notifierTous();
        Assertions.assertTrue(notifie1.get());
        Assertions.assertFalse(notifie2.get());
        // Avec 2 observateurs
        s.ajouteObservateur(obs2);
        notifie1.set(false);
        s.notifierTous();
        Assertions.assertTrue(notifie1.get());
        Assertions.assertTrue(notifie2.get());
    }

    @Test
    void retireObservateur() {
        ICollisionable bordures = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        Simulation s = new Simulation(bordures, Collections.emptyList());
        IObservateur<Simulation> obs = simu -> {};
        IObservateur<Simulation> obs2 = simu -> {};
        s.ajouteObservateur(obs);
        s.ajouteObservateur(obs2);
        s.retireObservateur(obs);
        Assertions.assertFalse(s.estObserve(obs));
        Assertions.assertTrue(s.estObserve(obs2));
    }

    @Test
    void testEquals() {
        ICollisionable bordures = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        Simulation s = new Simulation(bordures, Collections.emptyList());
        Simulation s2=s;
        // Tests par références
        Assertions.assertEquals(s,s2);
        s2 = null;
        Assertions.assertNotEquals(s, s2);
        // Tests par valeurs
        ICollisionable bordures2 = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 4, 4));
        s2 = new Simulation(bordures2, Collections.emptyList());
        Assertions.assertEquals(s,s2);
        bordures2 = ElementFactory.getInstance().obstacle(new Rectangle(0, 0, 42, 4));
        s2 = new Simulation(bordures2, Collections.emptyList());
        Assertions.assertNotEquals(s,s2);
    }
}