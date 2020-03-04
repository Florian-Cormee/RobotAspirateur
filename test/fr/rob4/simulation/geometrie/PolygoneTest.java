package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class PolygoneTest {

	@Test
	void testCollisionne() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDimension() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPoints() {
		fail("Not yet implemented");
	}

	@Test
	void testRotationDoublePoint2D() {
		fail("Not yet implemented");
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
		Polygone pol = new Polygone(1, 1, l);
		List<Segment> segs = pol.getSegments();
		assertEquals(3,segs.size());
		List<Segment> collSegs = Arrays.asList(new Segment(a,b), new Segment(b,c), new Segment(c,a));
		assertTrue(segs.containsAll(collSegs));
	}

	@Test
	void testIntersectePolygone() {
		fail("Not yet implemented");
	}

	@Test
	void testIntersecteRectangle() {
		fail("Not yet implemented");
	}

}
