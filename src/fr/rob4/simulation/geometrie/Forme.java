/**
 * 
 */
package fr.rob4.simulation.geometrie;

/**
 * Cette classe abstraite est une forme g�ometrique. Attribut de tous les
 * elements de la simulation.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 * @see Polygone
 */
public abstract class Forme {

	// Attribut
	protected Point2D centre;

	/**
	 * Cr�e une Forme � partir de son centre qui est un Point2D.
	 * 
	 * @param p Le point qui deviendra le centre de la forme.
	 */
	public Forme(Point2D p) {
		centre = p;
	}

	/**
	 * Cr�e une Forme � partir des coordonn�es de son centre.
	 * 
	 * @param x Abscisse du centre.
	 * @param y Ordonn�e du centre.
	 */
	public Forme(double x, double y) {
		centre = new Point2D(new Vecteur2D(x, y));
	}

	/**
	 * Obtient le centre.
	 * 
	 * @return Le centre (Point2D)
	 */
	public Point2D getCentre() {
		return centre;
	}

	/**
	 * Modifie le centre.
	 * 
	 * @param centre Le nouveau centre.
	 */
	public void setCentre(Point2D centre) {
		this.centre = centre;
	}

	/**
	 * Permet de savoir la Forme est supperpos�e avec l'autre forme {@code f}.
	 * 
	 * @param f Forme avec laquelle il fut tester la superposition.
	 * @return true si les formes sont superpos�es, false sinon.
	 */
	public boolean estSupperposee(Forme f) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtient une nouvelle forme identique � l'initiale tourn�e de alpha (en
	 * radian).
	 * <p>
	 * Note: L'instance n'est pas modifi�e.
	 * 
	 * @param alpha Angle de rotation en radian.
	 * @return La forme tourn�e de alpha dans une nouvelle instance.
	 */
	public Forme rotation(double alpha) {
		return rotation(alpha, centre);
	}

	/**
	 * Obtient une nouvelle forme identique � l'originale tourn�e de alpha (en
	 * radian) autour du point2D p.
	 * <p>
	 * Note: L'instance n'est pas modifi�e.
	 * 
	 * @param alpha Angle de rotation en radian.
	 * @param p     Point2D centre de la rotation.
	 * @return R�sultat de la rotation dans une nouvelle instance.
	 */
	public abstract Forme rotation(double alpha, Point2D p);

}
