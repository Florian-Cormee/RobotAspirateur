package fr.rob4.simulation.geometrie;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Vecteur2DTest {

    @Test
    public void addition() {
        Vecteur2D a = new Vecteur2D();
        Vecteur2D b = new Vecteur2D(2.5, -0.9);
        assertEquals(b, a.addition(b));

        a = new Vecteur2D(-0.5, 15);
        Vecteur2D c = b.addition(a);
        assertEquals(b.getX() + a.getX(), c.getX(), 1e-10);
        assertEquals(a.getY() + b.getY(), c.getY(), 1e-10);
    }

    @Test
    public void angle() {
        Vecteur2D a = new Vecteur2D(0, 1);
        assertEquals(0, a.angle(a), 1e-10);

        a = new Vecteur2D(1, 0);
        Vecteur2D b = new Vecteur2D(1, 1);
        assertEquals(Math.PI / 4, a.angle(b), 1e-10);
        b = new Vecteur2D(0, -1);
        assertEquals(-Math.PI / 2, a.angle(b), 1e-10);
        b = new Vecteur2D(-1, 0);
        assertEquals(Math.PI, a.angle(b), 1e-10);

        b = new Vecteur2D();
        try {
            double res = b.angle(a);
            Assert.fail(String.format("Attendait une erreur a obtenu: %f", res));
        } catch (Exception e) {
        }

    }

    @Test
    public void normalise() {
        Vecteur2D a = new Vecteur2D(Math.random(), Math.random());
        Vecteur2D b = a.normalise();
        assertEquals(0, a.angle(b), 1e-10);
        assertEquals(1, b.norme(), 1e-10);

        try {
            new Vecteur2D().normalise();
            Assert.fail("Attendait une erreur");
        } catch (Exception e) {
        }
    }

    @Test
    public void norme() {
        Random r = new Random();
        Vecteur2D a = new Vecteur2D(-r.nextDouble(), r.nextDouble());
        assertEquals(Math.sqrt(a.getX() * a.getX() + a.getY() * a.getY()), a.norme(), 1e-10);
    }

    @Test
    public void produit() {
        Random r = new Random();
        double coef = r.nextDouble() + 0.1;
        Vecteur2D a = new Vecteur2D(-r.nextDouble(), r.nextDouble());
        Vecteur2D b = a.produit(coef);
        assertEquals(0, a.angle(b), 1e-10);
        assertEquals(a.norme() * coef, b.norme(), 1e-10);
    }

    @Test
    public void rotation() {
        double angle = (Math.random() - 0.5) * 2 * Math.PI;
        Vecteur2D a = new Vecteur2D(1, 0);
        Vecteur2D b = a.rotation(angle);
        assertEquals(a.norme(), b.norme(), 1e-10);
        assertEquals(angle, a.angle(b), 1e-10);

        
        assertEquals(new Vecteur2D(),new Vecteur2D().rotation(10));
        
    }

    @Test
    public void scalaire() {
        Random r = new Random();
        Vecteur2D a = new Vecteur2D(-r.nextDouble(), r.nextDouble());
        Vecteur2D b = new Vecteur2D(r.nextDouble(), r.nextDouble());
        assertEquals(a.getX() * b.getX() + a.getY() * b.getY(), a.scalaire(b), 1e-10);
    }

    @Test
    public void soustraction() {
        Random r = new Random();
        Vecteur2D a = new Vecteur2D(-r.nextDouble(), r.nextDouble());
        Vecteur2D b = new Vecteur2D(r.nextDouble(), r.nextDouble());
        Vecteur2D c= a.soustraction(b);
        assertEquals(a.getX() - b.getX() , c.getX(), 1e-10);
        assertEquals(a.getY() - b.getY() , c.getY(), 1e-10);
    }
}