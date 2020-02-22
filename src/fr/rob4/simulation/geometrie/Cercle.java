/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * Cette classe représente un Cercle paramétré par son centre et son diametre.
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
	protected double diametre;

	/**
	 * Crée un nouveau Cercle à partir de son centre et de son diametre.
	 * 
	 * @param p Centre
	 * @param d Diametre
	 */
	public Cercle(Point2D p, double d) {
		super(p);
		diametre = d;
	}

	/**
	 * Crée un nouveau cercle à partir des coordonnées du centre et du diamètre.
	 * 
	 * @param x Abscisse du centre.
	 * @param y Ordonnée du centre.
	 * @param d Diametre
	 */
	public Cercle(double x, double y, double d) {
		super(x, y);
		diametre = d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
	 * fr.rob4.simulation.Point2D)
	 */
	@Override
	public Cercle rotation(double alpha, Point2D p) {
		Vecteur2D newPos = p.getPositionRelative(centre).rotation(alpha).addition(p.position);
		// Cercle newCercle = new Cercle(new Point2D(centre.origine, newPos),diametre);
		return new Cercle(new Point2D(centre.origine, newPos), diametre);
	}

	@Override
	public Cercle clone() {
		try {
			return (Cercle) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
