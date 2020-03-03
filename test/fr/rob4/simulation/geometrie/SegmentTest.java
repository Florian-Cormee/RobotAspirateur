package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.rob4.simulation.exception.NoIntersectionException;

class SegmentTest {

    @Test
    void testEstSuperposee() {
	fail("Not yet implemented");
    }

    @Test
    void testGetDimension() {
	Point2D p1 = new Point2D(new Vecteur2D(0, 0));
	Point2D p2 = new Point2D(new Vecteur2D(1, 0));
	Segment s = new Segment(p1, p2);
	Rectangle r = s.getDimension();
	assertEquals(1, r.getLargeur(), 1e-10);
	assertEquals(0, r.getHauteur(), 1e-10);
    }

    @Test
    void testSegment() {
	Point2D p1 = new Point2D(new Vecteur2D(0, 0));
	Point2D p2 = new Point2D(new Vecteur2D(1, 0));
	Segment s = new Segment(p1, p2);
	assertEquals(p1, s.getA());
	assertEquals(p2, s.getB());
    }

    @Test
    void testRotationDoublePoint2D() {
	Point2D p1 = new Point2D(new Vecteur2D(1, 0));
	Point2D p2 = new Point2D(new Vecteur2D(2, 0));
	Segment s = new Segment(p1, p2);
	Segment sr = s.rotation(Math.PI / 2, p1);
	assertEquals(s.getA(), sr.getA());
	p2 = new Point2D(new Vecteur2D(1, 1));
	assertEquals(p2, sr.getB());
    }

    @Test
    void testIntersecteSegment() {
	Point2D a = new Point2D(new Vecteur2D(-1, -1));
	Point2D b = new Point2D(new Vecteur2D(1, 1));
	Point2D c = new Point2D(new Vecteur2D(-1, 1));
	Point2D d = new Point2D(new Vecteur2D(1, -1));
	Segment s1 = new Segment(a, b);
	Segment s2 = new Segment(c, d);
	// Intersection de segment en leur milieu
	try {
	    Point2D inter = s1.intersecte(s2);
	    assertEquals(new Point2D(new Vecteur2D()), inter);
	} catch (NoIntersectionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    fail();
	}
	// Intersection de segments parallèles
	s2 = new Segment(a, b);
	try {
	    s1.intersecte(s2);
	    fail("Segment parallèles sans exception");
	} catch (NoIntersectionException e) {
	}
	// Intersection à l'extrémité
	s2 = new Segment(c, b);
	try {
	    Point2D inter = s1.intersecte(s2);
	    assertEquals(b, inter);
	} catch (NoIntersectionException e) {
	    e.printStackTrace();
	    fail();
	}
    }

    @Test
    void testIntersecteCercle() {
	Cercle cercle = new Cercle(0, 0, 1);
	Point2D a = new Point2D(new Vecteur2D(1.1, 0));
	Point2D b = new Point2D(new Vecteur2D(-1.1, 0));
	Segment s = new Segment(a, b);
	// Segment avec intersection
	try {
	    List<Point2D> l = s.intersecte(cercle);
	    assertEquals(2, l.size());
	    List<Point2D> collPts = Arrays.asList(new Point2D(new Vecteur2D(1, 0)), new Point2D(new Vecteur2D(-1, 0)));
	    assertTrue(l.containsAll(collPts));
	} catch (NoIntersectionException e) {
	    e.printStackTrace();
	    fail();
	}
	// Segment tangent
	s = new Segment(new Point2D(new Vecteur2D(-1, 1)), new Point2D(new Vecteur2D(1, 1)));
	try {
	    s.intersecte(cercle);
	    fail();
	} catch (NoIntersectionException e) {
	}

    }

    @Test
    void testIntersectePolygone() {
	fail("Not yet implemented");
    }

    @Test
    void testIntersecteRectangle() {
	fail("Not yet implemented");
    }

    @Test
    void testIntersecteArcDeCercle() {
	fail("Not yet implemented");
    }

    @Test
    void testNorme() {
	Point2D p1 = new Point2D(new Vecteur2D(1, 0));
	Point2D p2 = new Point2D(new Vecteur2D(2, 0));
	Segment s = new Segment(p1, p2);

	double norme = s.norme();
	assertEquals(1.0, norme, 1e-10);
    }

}
