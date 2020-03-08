package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rob4.simulation.exception.NoIntersectionException;

class ArcDeCercleTest {

	@Test
	void testCollisionne() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 3, Math.PI / 3);

		Segment s = new Segment(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(2, 0)));
		try {
			assertTrue(adc.collisionne(s));
		} catch (NoIntersectionException e) {
			fail();
		}

		Cercle c = new Cercle(1, -1, 1);
		try {
			assertTrue(adc.collisionne(c));
		} catch (NoIntersectionException e) {
			fail();
		}

		ArcDeCercle adc2 = new ArcDeCercle(1, -1, 1, Math.PI / 3, 2 * Math.PI / 3);
		try {
			assertTrue(adc.collisionne(adc2));
		} catch (NoIntersectionException e) {
			fail();
		}

		Rectangle r = new Rectangle(1, 1, 2, 2);
		try {
			assertTrue(adc.collisionne(r));
		} catch (NoIntersectionException e) {
			fail();
		}

		Polygone pol = new Polygone(Arrays.asList(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(3, 0)),
				new Point2D(new Vecteur2D(3, 1)), new Point2D(new Vecteur2D(0, 3))));
		try {
			assertTrue(adc.collisionne(pol));
		} catch (NoIntersectionException e) {
			fail();
		}
	}

	@Test
	void testIntersecteRectangle() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 3, Math.PI / 3);

		Rectangle r = new Rectangle(1, 1, 2, 2);
		try {
			List<Point2D> l = adc.intersecte(r);
			assertEquals(1, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		r = new Rectangle(2, 0, 2, 2);
		try {
			adc.intersecte(r);
			fail();
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	void testIntersectePolygone() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 3, Math.PI / 3);
		Polygone pol = new Polygone(Arrays.asList(new Point2D(new Vecteur2D()), new Point2D(new Vecteur2D(3, 0)),
				new Point2D(new Vecteur2D(3, 1)), new Point2D(new Vecteur2D(0, 3))));

		try {
			List<Point2D> l = adc.intersecte(pol);
			assertEquals(1, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testIntersecteArcDeCercle() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 3, Math.PI / 3);
		ArcDeCercle adc2 = new ArcDeCercle(1, -1, 1, Math.PI / 3, 2 * Math.PI / 3);
		try {
			List<Point2D> l = adc.intersecte(adc2);
			assertEquals(1, l.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1, 0)));
			assertTrue(l.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}

		ArcDeCercle adc3 = new ArcDeCercle(2, 0, 1, 5 * Math.PI / 6, -5 * Math.PI / 6);
		try {
			adc.intersecte(adc3);
			fail();
		} catch (NoIntersectionException e) {
		}
	}

	@Test
	void testEqualsObject() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 2, Math.PI / 2);
		ArcDeCercle adc2 = adc.deplace(new Vecteur2D());
		assertEquals(adc, adc2);
	}

	@Test
	void testRotationDoublePoint2D() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 6, Math.PI / 6);
		ArcDeCercle adc2 = adc.rotation(Math.PI / 2);
		assertEquals(Math.PI / 3, adc2.getOuverture(), 1e-10);
		assertEquals(2 * Math.PI / 6, adc2.ang1, 1e-10);
		assertEquals(4 * Math.PI / 6, adc2.ang2, 1e-10);

		ArcDeCercle adc3 = adc.rotation(Math.PI);
		assertEquals(Math.PI / 3, adc3.getOuverture(), 1e-10);
		assertEquals(5 * Math.PI / 6, adc3.ang1, 1e-10);
		assertEquals(-5 * Math.PI / 6, adc3.ang2, 1e-10);

		ArcDeCercle adc4 = new ArcDeCercle(1, 0, 1, Math.PI / 3, 2 * Math.PI / 3);
		ArcDeCercle adc5 = adc4.rotation(Math.PI / 2, new Point2D(new Vecteur2D()));
		assertEquals(new Vecteur2D(0, 1), adc5.getCentre().getPositionAbsolue());
		assertEquals(5 * Math.PI / 6, adc3.ang1, 1e-10);
		assertEquals(-5 * Math.PI / 6, adc3.ang2, 1e-10);
	}

	@Test
	void testGetOrientation() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 6, Math.PI / 6);
		assertEquals(0, adc.getOrientation(), 1e-10);

		ArcDeCercle adc2 = new ArcDeCercle(0, 0, 1, 5 * Math.PI / 6, -5 * Math.PI / 6);
		assertEquals(Math.PI, adc2.getOrientation(), 1e-10);
	}

	@Test
	void testGetOuverture() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 6, Math.PI / 6);
		assertEquals(Math.PI / 3, adc.getOuverture(), 1e-10);

		ArcDeCercle adc2 = new ArcDeCercle(0, 0, 1, 5 * Math.PI / 6, -5 * Math.PI / 6);
		assertEquals(Math.PI / 3, adc2.getOuverture(), 1e-10);
	}

	@Test
	void testDeplaceVecteur2D() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI / 2, Math.PI / 2);
		ArcDeCercle adc2 = adc.deplace(new Vecteur2D(1, 0));
		ArcDeCercle adcTest = new ArcDeCercle(1, 0, 1, -Math.PI / 2, Math.PI / 2);
		assertEquals(adcTest, adc2);
	}

}
