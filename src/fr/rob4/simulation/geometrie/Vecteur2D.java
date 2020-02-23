package fr.rob4.simulation.geometrie;

import java.util.Objects;

/**
 * Cette classe repr�sente un vecteur 2D avec une abscisse et une ordonn�e.
 * Permet de position tous les �l�ments graphique (Forme) dans leur
 * environement.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Forme
 */
public class Vecteur2D implements Cloneable {
	protected final double x;
	protected final double y;

	/**
	 * Crée un Vecteur2D nul.
	 */
	public Vecteur2D() {
		x = 0;
		y = 0;
	}

	/**
	 * Crée un Vecteur2D de coordonnées : (x,y).
	 * 
	 * @param x L'abscisse
	 * @param y L'ordonnée
	 */
	public Vecteur2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Obtient l'abscisse.
	 * 
	 * @return L'abscisse.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Obtient l'ordonnée
	 * 
	 * @return L'ordonnée
	 */
	public double getY() {
		return y;
	}

	/**
	 * Additionne avec {@code v}.
	 * <p>
	 * Note : L'instance n'est pas modifiée.
	 * 
	 * @param v Le vecteur à additionner.
	 * @return Le résultat de l'addition dans une nouvelle instance.
	 */
	public Vecteur2D addition(Vecteur2D v) {
		return new Vecteur2D(x + v.x, y + v.y);
	}

	/**
	 * Calcule l'angle avec {@code v}.
	 * 
	 * @param v Un vecteur
	 * @return L'angle en radian dans l'intervalle [-pi ; pi[
	 */
	public double angle(Vecteur2D v) {
		double prodNormes = norme() * v.norme();
		// Par définition du produit scalaire : s = ||this|| * ||v|| *
		// cos(angle)
		double cosAngle = scalaire(v) / prodNormes;
		// Par définition du produit vectoriel : ||pv|| = ||this|| * ||v|| *
		// sin(angle)
		double sinAngle = (x * v.y - v.x * y) / prodNormes;
		return Math.atan2(sinAngle, cosAngle);
	}

	/**
	 * Calcule la norme.
	 * 
	 * @return La norme
	 */
	public double norme() {
		return Math.sqrt(scalaire(this));
	}

	/**
	 * Crée un vecteur unitaire avec la même direction et même sens.
	 * <p>
	 * Note : L'instance n'est pas modifiée.
	 * 
	 * @return Un vecteur unitaire avec la même direction et même sens.
	 */
	public Vecteur2D normalise() {
		double norme = norme();
		return new Vecteur2D(x / norme, y / norme);
	}

	/**
	 * Multiplie avec un scalaire.
	 * <p>
	 * Note : L'instance n'est pas modifiée.
	 * 
	 * @param coef Le scalaire
	 * @return Le résultat du produit dans une nouvelle instance.
	 */
	public Vecteur2D produit(double coef) {
		return new Vecteur2D(x * coef, y * coef);
	}

	/**
	 * Fait tourner le vecteur autour de l'axe normale au plan 2D passant par (0,0).
	 * <p>
	 * Note : L'instance n'est pas modifiée.
	 * 
	 * @param angle L'angle de rotation
	 * @return Le vecteur tourné dans une nouvelle instance
	 */
	public Vecteur2D rotation(double angle) {
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		return new Vecteur2D(x * cosAngle + y * sinAngle, y * cosAngle - x * sinAngle);
	}

	/**
	 * Calcule le produit scalaire avec {@code v}.
	 * 
	 * @param v Un vecteur
	 * @return Le produit scalaire avec {@code v}
	 */
	public double scalaire(Vecteur2D v) {
		return x * v.x + y * v.y;
	}

	/**
	 * Soustrait {@code v}.
	 * <p>
	 * Note : L'instance n'est pas modifiée.
	 * 
	 * @param v Un vecteur
	 * @return Le résultat de la soustraction dans une nouvelle instance.
	 */
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
		return equals(vecteur2D, 1e-20);
	}

	public boolean equals(Vecteur2D v, double epsilon) {
		return Math.abs(x - v.x) < epsilon && Math.abs(y - v.y) < epsilon;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
