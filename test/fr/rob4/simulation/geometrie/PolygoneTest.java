package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rob4.simulation.exception.NoIntersectionException;

class PolygoneTest {

	@Test
	void testCollisionne() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDimension() {
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(1, -2));
		Point2D c = new Point2D(new Vecteur2D(2, 0));
		Point2D d = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(a);
		l.add(b);
		l.add(c);
		l.add(d);
		Polygone pol = new Polygone(l);
		Rectangle r = new Rectangle(1, 0, 2, 4);
		assertEquals(r, pol.getDimension());
	}

	@Test
	void testGetPoints() {
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(2, 0));
		Point2D c = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(a);
		l.add(b);
		l.add(c);
		Polygone pol = new Polygone(l);
		List<Point2D> listePts = pol.getPoints();
		assertEquals(3, listePts.size());
		assertTrue(listePts.containsAll(l));
	}

	@Test
	void testRotation() {
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(2, 0));
		Point2D c = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(a);
		l.add(b);
		l.add(c);
		Polygone pol = new Polygone(l);

		Polygone polR = pol.rotation(Math.PI/2, a);

		List<Point2D> liste = Arrays.asList(a, new Point2D(new Vecteur2D(0, 2)), new Point2D(new Vecteur2D(-2, 1)));
		assertTrue(polR.getPoints().containsAll(liste));
	}

	@Test
	void testGetSegments() {
		List<Point2D> l = new ArrayList<Point2D>();
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(2, 0));
		Point2D c = new Point2D(new Vecteur2D(1, 2));
		l.add(a);
		l.add(b);
		l.add(c);
		Polygone pol = new Polygone(l);
		List<Segment> segs = pol.getSegments();
		assertEquals(3, segs.size());
		List<Segment> collSegs = Arrays.asList(new Segment(a, b), new Segment(b, c), new Segment(c, a));
		assertTrue(segs.containsAll(collSegs));
	}

	@Test
	void testIntersectePolygone() {
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(2, 0));
		Point2D c = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(a);
		l.add(b);
		l.add(c);
		Polygone pol = new Polygone(l);

		Point2D d = new Point2D(new Vecteur2D(1, -1));
		Point2D e = new Point2D(new Vecteur2D(4, 0));
		Point2D f = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l2 = new ArrayList<Point2D>();
		l2.add(d);
		l2.add(e);
		l2.add(f);
		Polygone pol2 = new Polygone(l2);

		try {
			List<Point2D> liste = pol.intersecte(pol2);
			assertEquals(2, liste.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1, 0)), new Point2D(new Vecteur2D(1, 2)));
			assertTrue(liste.containsAll(collPts));
		} catch (NoIntersectionException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	@Test
	void testIntersecteRectangle() {
		Point2D a = new Point2D(new Vecteur2D());
		Point2D b = new Point2D(new Vecteur2D(1, -2));
		Point2D c = new Point2D(new Vecteur2D(2, 0));
		Point2D d = new Point2D(new Vecteur2D(1, 2));
		List<Point2D> l = new ArrayList<Point2D>();
		l.add(a);
		l.add(b);
		l.add(c);
		l.add(d);
		Polygone pol = new Polygone(l);
		Rectangle r = new Rectangle(c, 2, 2);

		try {
			List<Point2D> liste = pol.intersecte(r);
			assertEquals(2, liste.size());
			List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1.5, 1)),
					new Point2D(new Vecteur2D(1.5, -1)));
			assertTrue(liste.containsAll(collPts));
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			fail();
		}
	}

}
