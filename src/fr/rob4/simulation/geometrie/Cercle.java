/**
 * 
 */
package fr.rob4.simulation.geometrie;



/**
 * @author flore
 *
 */
public class Cercle extends Forme {

	// Attribut
	protected double diametre;
	/**
	 * @param p
	 */
	public Cercle(Point2D p , double d) {
		super(p);
		diametre = d;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Cercle(double x, double y, double d) {
		super(x, y);
		diametre = d;
	}

	/* (non-Javadoc)
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double, fr.rob4.simulation.Point2D)
	 */
	@Override
	public Forme rotation(double alpha, Point2D p) {
		Vecteur2D newPos = p.getPositionRelative(centre).rotation(alpha).addition(p.position);
		Cercle newCercle = new Cercle(new Point2D(centre.origine, newPos),diametre);
		return newCercle;
	}

}
