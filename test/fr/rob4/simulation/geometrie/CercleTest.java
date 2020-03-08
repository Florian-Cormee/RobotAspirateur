package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rob4.simulation.exception.NoIntersectionException;

class CercleTest {

	@Test
	void testCollisionne() {
		Cercle c = new Cercle(0, 0, 2);

		Segment s = new Segment(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(3, 3)));
		try {
			assertTrue(c.collisionne(s));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		Cercle cercle = new Cercle(2, 0, 3);
		try {
			assertTrue(c.collisionne(cercle));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		ArcDeCercle adc = new ArcDeCercle(2, 0, 1, 5 * Math.PI / 6, -Math.PI / 3);
		try {
			assertTrue(c.collisionne(adc));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		Rectangle r = new Rectangle(0, 0, 1, 5);
		try {
			assertTrue(c.collisionne(r));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		Polygone pol = new Polygone(Arrays.asList(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(0, -3)),
				new Point2D(new Vecteur2D(5, 0)), new Point2D(new Vecteur2D(2, 2))));
		try {
			assertTrue(c.collisionne(pol));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testGetDimension() {
		Cercle c = new Cercle(0, 0, 1);
		Rectangle r = new Rectangle(0, 0, 2, 2);
		assertEquals(r, c.getDimension());
	}

	@Test
	void testRotationDoublePoint2D() {
		Cercle c = new Cercle(1, 0, 1);
		Point2D OO = new Point2D(new Vecteur2D()); // Point en (0, 0)

		Cercle c2 = c.rotation(Math.PI / 2, OO);
		Cercle cTest = new Cercle(0, 1, 1);
		assertEquals(cTest, c2);
	}

	@Test
	void testDeplaceVecteur2D() {
		Cercle c = new Cercle(1, 0, 1);
		Cercle c2 = c.deplace(new Vecteur2D(1, 0));
		Cercle cTest = new Cercle(2, 0, 1);
		assertEquals(cTest, c2);
	}

	@Test
	void testIntersecteCercle() {
		Cercle c = new Cercle(-1, 0, 1);
		Cercle c2 = new Cercle(1, 0, 1);
		try {
			c.intersecte(c2);
			fail();
		} catch (NoIntersectionException e) {
		}

		Cercle c3 = new Cercle(-1, 0, 2);
		Cercle c4 = new Cercle(1, 0, 2);
		try {
			List<Point2D> l = c3.intersecte(c4);
			Segment s = new Segment(new Point2D(new Vecteur2D(0, 2)), new Point2D(new Vecteur2D(0, -2)));
			List<Point2D> listeTest = s.intersecte(c3);
			assertEquals(2, l.size());
			assertTrue(l.containsAll(listeTest));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testIntersecteRectangle() {
		Cercle c = new Cercle(0, 0, 2);
		Rectangle r = new Rectangle(2, 1, 4, 2);
		try {
			List<Point2D> l = c.intersecte(r);
			assertEquals(2, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(0, 2)), new Point2D(new Vecteur2D(2, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testIntersectePolygone() {
		Cercle c = new Cercle(0, 0, 2);
		Polygone pol = new Polygone(Arrays.asList(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(2, 0)),
				new Point2D(new Vecteur2D(1, 1))));
		try {
			List<Point2D> l = c.intersecte(pol);
			assertEquals(1, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(2, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	void testIntersecteArcDeCercle() {
		Cercle c = new Cercle(0, 0, 1);
		ArcDeCercle adc = new ArcDeCercle(2, 0, 1, 2 * Math.PI / 3, -2 * Math.PI / 3);

		// Arc de cercle tangent
		try {
			c.intersecte(adc);
			fail();
		} catch (NoIntersectionException e) {
		}

		// Arc de cercle orienté du mauvais coté
		ArcDeCercle adc2 = new ArcDeCercle(1.5, 0, 1, -Math.PI / 3, Math.PI / 3);
		try {
			c.intersecte(adc2);
			fail();
		} catch (NoIntersectionException e) {
		}

		ArcDeCercle adc3 = new ArcDeCercle(-1, -1, 1, Math.PI / 3, 2 * Math.PI / 3);
		try {
			List<Point2D> l = c.intersecte(adc3);
			assertEquals(1, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(-1, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testEqualsObject() {
		Cercle c = new Cercle(0, 0, 1);
		Cercle c2 = c.deplace(new Vecteur2D());
		assertTrue(c.equals(c2));
	}
}
