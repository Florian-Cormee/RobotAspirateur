package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class CapteurContactTest {

    @Test
    void actualise() {
        Rectangle r = new Rectangle(0, 0, 4, 4);
        Obstacle bordure = new Obstacle(r);
        Forme f = new Cercle(0, 0, 0.1);
        Obstacle o = new Obstacle(f);
        Simulation s = new Simulation(bordure, Arrays.asList(o));

        ArcDeCercle adc = new ArcDeCercle(-1, 0, 1, -Math.PI / 2, Math.PI / 2);
        CapteurContact cc = new CapteurContact(adc);
        cc.actualise(s);
        Assertions.assertTrue(cc.getInfo());

        s = new Simulation(bordure, Collections.emptyList());
        cc.actualise(s);
        Assertions.assertFalse(cc.getInfo());

        cc.translation(new Vecteur2D(0, 2));
        cc.actualise(s);
        Assertions.assertTrue(cc.getInfo());
    }
}