package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RectangleTest {

	@Test
	void testCollisionne() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDimension() {
		Rectangle r = new Rectangle(0, 0, 2, 2);
		Rectangle dim = r.getDimension();
		assertEquals(r,dim);
	}

	@Test
	void testRotationDoublePoint2D() {
		fail("Not yet implemented");
	}

	@Test
	void testEqualsObject() {
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

}
