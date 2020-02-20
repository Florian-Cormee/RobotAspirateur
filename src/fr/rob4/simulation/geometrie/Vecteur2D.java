package fr.rob4.simulation.geometrie;

import java.util.Objects;

/**
 * @author flore
 */
public class Vecteur2D implements Cloneable {

	// Attributs
	protected double x;
	protected double y;

	public Vecteur2D() {
		x = 0;
		y = 0;
	}

	public Vecteur2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vecteur2D addition(Vecteur2D v) {
		return new Vecteur2D(x + v.x, y + v.y);
	}

	public double getAngle(Vecteur2D v) {
		double prodNormes = norme() * v.norme();
		// Par définition du produit scalaire : s = ||this|| * ||v|| * cos(angle)
		double cosAngle = scalaire(v) / prodNormes;
		// Par définition du produit vectoriel : ||pv|| = ||this|| * ||v|| * sin(angle)
		double sinAngle = (x * v.y - v.x * y) / prodNormes;
		return Math.atan2(sinAngle, cosAngle);
	}

	public double norme() {
		return Math.sqrt(scalaire(this));
	}

	public Vecteur2D normalise() {
		double norme = norme();
		return new Vecteur2D(x / norme, y / norme);
	}

	public Vecteur2D produit(double coef) {
		return new Vecteur2D(x * coef, y * coef);
	}

	public Vecteur2D rotation(double angle) {
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		return new Vecteur2D(x * cosAngle + y * sinAngle, y * cosAngle - x * sinAngle);
	}

	public double scalaire(Vecteur2D v) {
		return x * v.x + y * v.y;
	}

	public Vecteur2D soustraction(Vecteur2D v) {
		return new Vecteur2D(x - v.x, y - v.y);
	}

	@Override
	public Vecteur2D clone() {
		try {
			return (Vecteur2D) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Vecteur2D{" + "x=" + x + ", y=" + y + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Vecteur2D vecteur2D = (Vecteur2D) o;
		return equals(vecteur2D, 1e-10);
	}

	public boolean equals(Vecteur2D v, double epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
