/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * @author flore
 *
 */
public class ArcDeCercle extends Cercle {

	// Attributs
	protected double ang1;
	protected double ang2;

	/**
	 * @param p
	 * @param d
	 */
	public ArcDeCercle(Point2D p, double d, double a1, double a2) {
		super(p, d);
		ang1 = a1;
		ang2 = a2;
	}

	/**
	 * @param x
	 * @param y
	 * @param d
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

	public double getOuverture() {
		return Math.max(ang1, ang2) - Math.min(ang1, ang2);
	}

	public double getOrientation() {
		return getOuverture() / 2;
	}

	public void setOuverture(double o) {
		double mid = o / 2; // la moitié de la nouvelle ouverture
		// On modifie les angles pour avoir la bonne ouverture autour de l'orientation
		// actuelle
		ang1 = getOrientation() + mid;
		ang2 = getOrientation() - mid;
	}

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
