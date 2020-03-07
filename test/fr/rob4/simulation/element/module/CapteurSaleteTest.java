package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.element.Tache;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

class CapteurSaleteTest {

    @Test
    void actualise() {
        Rectangle r = new Rectangle(0, 0, 4, 4);
        Obstacle bordure = new Obstacle(r);
        Forme f = new Cercle(0, 0, 0.1);
        Tache o = new Tache(f);
        Simulation s = new Simulation(bordure, Arrays.asList(o));

        Cercle c = new Cercle(0, 0.1, 0.05);
        CapteurSalete cs = new CapteurSalete(c);

        cs.actualise(s);
        Assertions.assertTrue(cs.getInfo());
    }

    @Test
    void getNettoyables() {
        Rectangle r = new Rectangle(0, 0, 4, 4);
        Obstacle bordure = new Obstacle(r);
        Forme f = new Cercle(0, 0, 0.1);
        Tache t1 = new Tache(f);
        Tache t2 = new Tache(f);
        t2.translation(new Vecteur2D(1, 0));
        Simulation s = new Simulation(bordure, Arrays.asList(t1,t2));

        Cercle c = new Cercle(0, 0.1, 0.05);
        CapteurSalete cs = new CapteurSalete(c);

        cs.actualise(s);
        Set<INettoyable> nettoyables = cs.getNettoyables();
        Assertions.assertTrue(nettoyables.contains(t1));
        Assertions.assertEquals(1, nettoyables.size());
    }
}