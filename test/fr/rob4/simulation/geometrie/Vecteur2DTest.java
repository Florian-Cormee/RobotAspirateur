package fr.rob4.simulation.geometrie;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Vecteur2DTest {

    @Test
    public void addition() {
        Vecteur2D a = new Vecteur2D();
        Vecteur2D b = new Vecteur2D(2.5, -0.9);
        assertEquals(b, a.addition(b));

        a = new Vecteur2D(-0.5, 15);
        Vecteur2D c = b.addition(a);
        assertEquals(b.getX() + a.getX(), c.getX(), 1e-20);
        assertEquals(a.getY() + b.getY(), c.getY(), 1e-20);
    }

    @Test
    public void angle() {
        Vecteur2D a = new Vecteur2D(0, 1);
        assertEquals(0, a.angle(a), 1e-20);

        Vecteur2D b = new Vecteur2D();
        try {
            b.angle(a);
            Assert.fail("Attendait une erreur");
        } catch (Exception e) {
        }
        b = new Vecteur2D(1,1);
    }

    @Test
    public void normalise() {
    }

    @Test
    public void norme() {
    }

    @Test
    public void produit() {
    }

    @Test
    public void rotation() {
    }

    @Test
    public void scalaire() {
    }

    @Test
    public void soustraction() {
    }
}