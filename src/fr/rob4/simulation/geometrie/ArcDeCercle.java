/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * Cette classe représente un arc de cercle. C'est une portion de cercle. On
 * peut le manipuler grâce à 2 angles, la bisectrice de ces angles définissant
 * son orientation et son ouverture, la différence de ces 2 angles. Classe fille
 * de Cercle.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see Rectangle
 * @see Polygone
 */
public class ArcDeCercle extends Cercle {

	// Attributs
	protected double ang1;
	protected double ang2;

	/**
	 * Crée un arc de cercle à partir de son centre (Point2D), son diametre, et les
	 * 2 angles définissant son ouverture et son orientation.
	 * 
	 * @param p  Centre
	 * @param d  Diametre
	 * @param a1 1er angle
	 * @param a2 2eme angle
	 */
	public ArcDeCercle(Point2D p, double d, double a1, double a2) {
		super(p, d);
		ang1 = a1;
		ang2 = a2;
	}

	/**
	 * Crée un arc de cecle à partir des coordonnées de son centre, son diametre, et
	 * les 2 angles définissant son ouverture et son orientatioon.
	 * 
	 * @param x  Abscisse du centre
	 * @param y  Ordonnée du centre
	 * @param d  Diametre
	 * @param a1 1er angle
	 * @param a2 2eme angle
	 */
	public ArcDeCercle(double x, double y, double d, double a1, double a2) {
		super(x, y, d);
		ang1 = a1;
		ang2 = a2;
	}

	/*
	 * public ArcDeCercle(Cercle c, double a1, double a2) { Cercle cercle =
	 * c.clone(); centre = cercle.centre; diametre = cercle.diametre; ang1 = a1;
	 * ang2 = a2; }
	 */

	/**
	 * Obtient l'ouverture de l'arc de cercle.
	 * 
	 * @return Ouverture, un angle.
	 */
	public double getOuverture() {
		return Math.max(ang1, ang2) - Math.min(ang1, ang2);
	}

	/**
	 * Obtient l'orientation de l'arc de cercle.
	 * 
	 * @return Orientation, un angle.
	 */
	public double getOrientation() {
		return getOuverture() / 2;
	}

	/**
	 * Modifie l'ouverture avec un nouvel angle d'ouverture.
	 * 
	 * @param o Nouvelle ouverture.
	 */
	public void setOuverture(double o) {
		double mid = o / 2; // la moitié de la nouvelle ouverture
		// On modifie les angles pour avoir la bonne ouverture autour de l'orientation
		// actuelle
		ang1 = getOrientation() + mid;
		ang2 = getOrientation() - mid;
	}

	/**
	 * Modifie l'orientation avec un nouvel angle.
	 * 
	 * @param o Orientation, un angle.
	 */
	public void setOrientation(double o) {
		double mid = getOuverture() / 2; // la moitié de l'ouverture actuelle
		// On modofie les angles pour que l'ouverture soit la même autour de la
		// nouvelle orientation
		ang1 = o + mid;
		ang2 = o - mid;
	}

	@Override
	public ArcDeCercle rotation(double alpha, Point2D p) {
		ArcDeCercle newADC = (ArcDeCercle) super.rotation(alpha, p);
		newADC.setOrientation(getOrientation() + alpha);
		return newADC;
	}

}
