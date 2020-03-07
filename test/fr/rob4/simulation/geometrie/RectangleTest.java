package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rob4.simulation.exception.NoIntersectionException;

class RectangleTest {

	@Test
	void testCollisionne() {
		Rectangle rect = new Rectangle(0, 0, 2, 2);
		
		Segment seg = new Segment(new Point2D(new Vecteur2D(0, -2)), new Point2D(new Vecteur2D(1, 3)));
		System.out.println(seg);
		try {
			System.out.println(seg.intersecte(rect));
			assertTrue(rect.collisionne(seg));
		}catch(NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
		
		Cercle cercle = new Cercle(0.5, -0.5, 1.4);
		try {
			assertTrue(rect.collisionne(cercle));
		}catch(NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testGetDimension() {
		Rectangle r = new Rectangle(0, 0, 2, 2);
		Rectangle dim = r.getDimension();
		assertEquals(r,dim);
	}

	@Test
	void testRotationDoublePoint2D() {
		try {
			Rectangle r = new Rectangle(2, 0, 2, 2);
			Polygone pol = r.rotation(Math.PI/2, new Point2D(new Vecteur2D()));
			List<Point2D> l = Arrays.asList(new Point2D(new Vecteur2D(1, 1)), new Point2D(new Vecteur2D(1, 3)), new Point2D(new Vecteur2D(-1, 3)), new Point2D(new Vecteur2D(-1, 1)));
			assertTrue(pol.getPoints().containsAll(l));
		}catch (NullPointerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testEqualsObject() {
		Rectangle r = new Rectangle(0, 0, 1, 1);
		Rectangle r2 = new Rectangle(0, 0, 1, 1);
		assertTrue(r.equals(r2));
		Rectangle r3 = new Rectangle(0, 1, 1, 1);
		assertFalse(r.equals(r3));		
	}

	@Test
	void testToPolygone() {
		Rectangle r = new Rectangle(0, 0, 2, 2);
		Point2D a = new Point2D(new Vecteur2D(-1, -1));
		Point2D b = new Point2D(new Vecteur2D(-1, 1));
		Point2D c = new Point2D(new Vecteur2D(1, 1));
		Point2D d = new Point2D(new Vecteur2D(1, -1));
		Polygone pol = r.toPolygone();
		List<Point2D> collPts = Arrays.asList(a,b,c,d);
		assertTrue(pol.getPoints().containsAll(collPts));
	}

	@Test
	void testIntersecte() {
		Rectangle r = new Rectangle(0, 0, 1, 1);
		Rectangle r2 = new Rectangle(0.5, 0.5, 1, 1);
		try {
			List<Point2D> coll = r.intersecte(r2);
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(0.5, 0)), new Point2D(new Vecteur2D(0, 0.5)));
			assertTrue(coll.containsAll(collPts));
		}catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
		Rectangle r3 = new Rectangle(-0.5, -0.5, 1, 1);
		try {
			List<Point2D> coll = r.intersecte(r3);
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(-0.5, 0)), new Point2D(new Vecteur2D(0, -0.5)));
			assertTrue(coll.containsAll(collPts));
		}catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

}
