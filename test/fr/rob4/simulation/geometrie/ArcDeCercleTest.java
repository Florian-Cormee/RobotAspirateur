package fr.rob4.simulation.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArcDeCercleTest {

	@Test
	void testCollisionne() {
		fail("Not yet implemented");
	}

	@Test
	void testIntersecteRectangle() {
		fail("Not yet implemented");
	}

	@Test
	void testIntersectePolygone() {
		fail("Not yet implemented");
	}

	@Test
	void testIntersecteArcDeCercle() {
		fail("Not yet implemented");
	}

	@Test
	void testEqualsObject() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI/2, Math.PI/2);
		ArcDeCercle adc2 = adc.deplace(new Vecteur2D());
		assertEquals(adc, adc2);
	}

	@Test
	void testRotationDoublePoint2D() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOrientation() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOuverture() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI/6, Math.PI/6);
		assertEquals(Math.PI/3, adc.getOuverture(), 1e-10);
		
		ArcDeCercle adc2 = new ArcDeCercle(0, 0, 1, 5*Math.PI/6, -5*Math.PI/6);
		assertEquals(Math.PI/3, adc2.getOuverture(), 1e-10);
	}

	@Test
	void testDeplaceVecteur2D() {
		ArcDeCercle adc = new ArcDeCercle(0, 0, 1, -Math.PI/2, Math.PI/2);
		ArcDeCercle adc2 = adc.deplace(new Vecteur2D(1, 0));
		ArcDeCercle adcTest = new ArcDeCercle(1, 0, 1, -Math.PI/2, Math.PI/2);
		assertEquals(adcTest,adc2);
	}

}
