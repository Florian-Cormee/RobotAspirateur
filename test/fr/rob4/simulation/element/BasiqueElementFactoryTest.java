package fr.rob4.simulation.element;

import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BasiqueElementFactoryTest {

    @Test
    void capteurContact() {
        BasiqueElementFactory bef = new BasiqueElementFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.capteurContact(null));
        Assertions.assertSame(CapteurContact.class, bef.capteurContact(new Cercle(1, 42, 12)).getClass());
    }

    @Test
    void capteurSalete() {
        BasiqueElementFactory bef = new BasiqueElementFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.capteurSalete(null));
        Assertions.assertSame(CapteurSalete.class, bef.capteurSalete(new Rectangle(1, 0, 42, 12)).getClass());
    }

    @Test
    void obstacle() {
        BasiqueElementFactory bef = new BasiqueElementFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.obstacle(null));
        Rectangle rectangle = new Rectangle(0, 42, 1, 0.5);
        ICollisionable obstacle = bef.obstacle(rectangle);
        Assertions.assertSame(Obstacle.class, obstacle.getClass());
    }

    @Test
    void robot() {
        BasiqueElementFactory bef = new BasiqueElementFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.robot(null, r -> {}, 0, 0));
        Assertions.assertThrows(NullPointerException.class, () -> bef.robot(new Cercle(0, 0, 0.1), null, 0, 0));
        IRobot robot = bef.robot(new Cercle(0, 1, 2), r -> {}, Math.PI, 4);
        Assertions.assertSame(Robot.class, robot.getClass());
    }

    @Test
    void tache() {
        BasiqueElementFactory bef = new BasiqueElementFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.obstacle(null));
        Rectangle rectangle = new Rectangle(0, 42, 1, 0.5);
        INettoyable obstacle = bef.tache(rectangle);
        Assertions.assertSame(Tache.class, obstacle.getClass());
    }
}