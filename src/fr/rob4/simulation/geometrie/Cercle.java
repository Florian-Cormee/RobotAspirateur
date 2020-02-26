/**
 *
 */
package fr.rob4.simulation.geometrie;

/**
 * Cette classe représente un Cercle paramétré par son centre et son rayon.
 * Classe fille de Forme.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see ArcDeCercle
 * @see Polygone
 * @see Rectangle
 */
public class Cercle extends Forme {

	// Attribut
	protected double rayon;

	/**
	 * Crée un nouveau Cercle à partir de son centre et de son rayon.
	 *
	 * @param p Centre
	 * @param d rayon
	 */
	public Cercle(Point2D p, double d) {
		super(p);
		rayon = d;
	}

	/**
	 * Crée un nouveau cercle à partir des coordonnées du centre et du diamètre.
	 *
	 * @param x Abscisse du centre.
	 * @param y Ordonnée du centre.
	 * @param d rayon
	 */
	public Cercle(double x, double y, double d) {
		super(x, y);
		rayon = d;
	}

	@Override
	public Cercle clone() {
		try {
			return (Cercle) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	/**
	 * Obtient le rayon
	 * 
	 * @return Le rayon
	 */
	public double getRayon() {
		return rayon;
	}

	@Override
	public Cercle rotation(double alpha, Point2D p) {
		Vecteur2D newPos = p.getPositionRelative(centre).rotation(alpha).addition(p.position);
		// Cercle newCercle = new Cercle(new Point2D(centre.origine, newPos),rayon);
		return new Cercle(new Point2D(centre.origine, newPos), rayon);
	}

	@Override
	public Rectangle getDimension() {
		return new Rectangle(centre, rayon*2, rayon*2);
	}

}
