/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * @author flore
 *
 */
public class Vecteur2D {

	// Attributs
	protected double x;
	protected double y;

	public Vecteur2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	/*
	 * public void setX(double x) { this.x = x; }
	 */

	public double getY() {
		return y;
	}

	/*
	 * public void setY(double y) { this.y = y; }
	 */

	public Vecteur2D addition(Vecteur2D v) {
		return new Vecteur2D(x + v.x, y + v.y);
	}

	public Vecteur2D multiplie(double coef) {
		return new Vecteur2D(x * coef, y * coef);
	}

	public Vecteur2D soustraction(Vecteur2D v) {
		return addition(v.multiplie(-1));
	}

	public double scalaire(Vecteur2D v) {
		return x * v.x + y * v.y;
	}

	public double norme() {
		return Math.sqrt(scalaire(this));
	}
	
	
}
