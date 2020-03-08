package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class Point2DTest {

    @Test
    void getPositionAbsolue() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	assertEquals(1, p.getPositionAbsolue().getX(), 1e-10);
	assertEquals(1, p.getPositionAbsolue().getY(), 1e-10);
	Point2D p2 = new Point2D(p, new Vecteur2D(1, 2));
	assertEquals(2, p2.getPositionAbsolue().getX(), 1e-10);
	assertEquals(3, p2.getPositionAbsolue().getY(), 1e-10);
    }

    @Test
    void getPositionRelativePoint2D() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	Point2D p2 = new Point2D(new Vecteur2D(1, 2));
	Vecteur2D vpr = p.getPositionRelative(p2);
	assertEquals(0, vpr.getX(), 1e-10);
	assertEquals(1, vpr.getY(), 1e-10);
	Point2D p3 = new Point2D(p, new Vecteur2D(1, 2));
	Vecteur2D vpr2 = p.getPositionRelative(p3);
	assertEquals(1, vpr2.getX(), 1e-10);
	assertEquals(2, vpr2.getY(), 1e-10);
    }

    @Test
    void deplace() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	Point2D p2 = p.deplace(new Vecteur2D(5, 5));
	assertEquals(6, p2.position.getX(), 1e-10);
	assertEquals(6, p2.position.getY(), 1e-10);
    }

    @Test
    void testToString() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	assertEquals("Point2D{origine=null, position=Vecteur2D{x=1.0, y=1.0}}", p.toString());
    }

    @Test
    void equalsObject() {
	Vecteur2D v = new Vecteur2D(1, 1);
	Point2D p = new Point2D(v);
	assertEquals(p, p);
	assertNotEquals(p, v);
	Point2D p2 = new Point2D(v);
	assertEquals(p, p2);
	Point2D p3 = new Point2D(p, new Vecteur2D());
	assertNotEquals(p, p3);
    }

    @Test
    void testClone() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	Point2D pclone = p.clone();
	assertEquals(p, pclone);
	Point2D p2 = pclone.deplace(new Vecteur2D(1, 1));
	assertNotEquals(p, p2);
    }

    @Test
    void rotationOrigine() {
	Point2D p = new Point2D(new Vecteur2D(1, 0));
	Point2D p2 = p.rotationOrigine(Math.PI / 2);
	assertEquals(0, p2.position.getX(), 1e-10);
	assertEquals(1, p2.position.getY(), 1e-10);
    }

    @Test
    void rotation() {
	Point2D p = new Point2D(new Vecteur2D(1, 1));
	Point2D p2 = new Point2D(p, new Vecteur2D(0, -1));
	Point2D origine = new Point2D(new Vecteur2D(0, 0));
	Point2D p3 = p2.rotation(Math.PI / 2, origine);
	assertEquals(-1, p3.position.getX(), 1e-10);
	assertEquals(0, p3.position.getY(), 1e-10);
    }
}
