package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SegmentTest {

    @Test
    void testEstSuperposee() {
	fail("Not yet implemented");
    }

    @Test
    void testGetDimension() {
	fail("Not yet implemented");
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
	Segment sr = s.rotation(Math.PI/2, p1);
	assertEquals(s.getA(), sr.getA());
	p2 = new Point2D(new Vecteur2D(1, 1));
	assertEquals(p2, sr.getB());
    }

    @Test
    void testIntersecteSegment() {
	fail("Not yet implemented");
    }

    @Test
    void testIntersecteCercle() {
	fail("Not yet implemented");
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
	fail("Not yet implemented");
    }

}
