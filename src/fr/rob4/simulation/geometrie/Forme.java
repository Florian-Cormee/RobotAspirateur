/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * @author flore
 *
 */
public abstract class Forme {

	// Attribut
	protected Point2D centre;

	public Forme(Point2D p) {
		centre = p;
	}

	public Forme(double x, double y) {
		centre = new Point2D(new Vecteur2D(x, y));
	}

	public Point2D getCentre() {
		return centre;
	}

	public void setCentre(Point2D centre) {
		this.centre = centre;
	}

	public boolean estSupperposee(Forme f) {
		throw new UnsupportedOperationException();
	}

	public Forme rotation(double alpha) {
		return rotation(alpha, centre);
	}

	public abstract Forme rotation(double alpha, Point2D p);

}
