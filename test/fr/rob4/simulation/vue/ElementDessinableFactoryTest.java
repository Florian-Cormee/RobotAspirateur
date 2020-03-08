package fr.rob4.simulation.vue;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.vue.element.TacheDessinable;
import fr.rob4.simulation.vue.element.ObstacleDessinable;
import fr.rob4.simulation.vue.element.RobotDessinable;
import fr.rob4.simulation.vue.element.module.CapteurContactDessinable;
import fr.rob4.simulation.vue.element.module.CapteurSaleteDessinable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ElementDessinableFactoryTest {

    @Test
    void capteurContact() {
        ElementDessinableFactory bef = new ElementDessinableFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.capteurContact(null));
        Assertions.assertSame(CapteurContactDessinable.class, bef.capteurContact(new Cercle(1, 42, 12)).getClass());
    }

    @Test
    void capteurSalete() {
        ElementDessinableFactory bef = new ElementDessinableFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.capteurSalete(null));
        Assertions.assertSame(CapteurSaleteDessinable.class, bef.capteurSalete(new Rectangle(1, 0, 42, 12)).getClass());
    }

    @Test
    void obstacle() {
        ElementDessinableFactory bef = new ElementDessinableFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.obstacle(null));
        Rectangle rectangle = new Rectangle(0, 42, 1, 0.5);
        ICollisionable obstacle = bef.obstacle(rectangle);
        Assertions.assertSame(ObstacleDessinable.class, obstacle.getClass());
    }

    @Test
    void robot() {
        ElementDessinableFactory bef = new ElementDessinableFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.robot(null, r -> {}, 0, 0));
        Assertions.assertThrows(NullPointerException.class, () -> bef.robot(new Cercle(0, 0, 0.1), null, 0, 0));
        IRobot robot = bef.robot(new Cercle(0, 1, 2), r -> {}, Math.PI, 4);
        Assertions.assertSame(RobotDessinable.class, robot.getClass());
    }

    @Test
    void tache() {
        ElementDessinableFactory bef = new ElementDessinableFactory();
        Assertions.assertThrows(NullPointerException.class, () -> bef.obstacle(null));
        Rectangle rectangle = new Rectangle(0, 42, 1, 0.5);
        INettoyable obstacle = bef.tache(rectangle);
        Assertions.assertSame(TacheDessinable.class, obstacle.getClass());
    }
}