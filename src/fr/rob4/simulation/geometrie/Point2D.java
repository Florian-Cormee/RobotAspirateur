package fr.rob4.simulation.geometrie;

import java.util.Objects;

/**
 * Cette classe repr�sente un Point2D. Il connait son rep�re d'origine �galement
 * rep�sent� par un Point2D. El�ment principale des Formes
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Vecteur2D
 * @see Forme
 */
public class Point2D {

	// Attributs
	public static final Point2D ORIGINE = new Point2D(null, new Vecteur2D());
	
	protected Point2D origine;
	protected Vecteur2D position;

	/**
	 * Constructeur � partir d'un Point2D d'origine et sa position dans ce rep�re (Vecteur2D).
	 * @param origine Point2D repr�sentant le rep�re d'origine du nouveau point.
	 * @param position Vecteur2D qui d�fini la position du nouveau point dans son rep�re.
	 */
	public Point2D(Point2D origine, Vecteur2D position) {
		this.origine = origine;
		this.position = position;
	}

	/**
	 * Constructeur 
	 * @param position
	 */
	public Point2D(Vecteur2D position) {
		this(ORIGINE, position);
	}

	public Point2D(double x, double y) {
		this(new Vecteur2D(x, y));
	}

	public Point2D getOrigine() {
		return origine;
	}

	/**
	 * M�thode retournant la position du point2D par rapport � son origine
	 * 
	 * @return position : Vecteur2D
	 */
	public Vecteur2D getPositionRelative() {
		return position;
	}

	public Vecteur2D getPositionAbsolue() {
		if (origine == null) {
			return position;
		}
		return position.addition(origine.getPositionAbsolue());
	}

	/**
	 * Obtient le vecteur partant de ce point vers p
	 *
	 * @param p Le point
	 * @return Le vecteur partant de ce point vers p
	 */
	public Vecteur2D getPositionRelative(Point2D p) {
		Vecteur2D posAbs = getPositionAbsolue();
		Vecteur2D pPosAbs = p.getPositionAbsolue();
		return pPosAbs.soustraction(posAbs);
	}

	public Point2D deplace(Vecteur2D v) {
		return new Point2D(origine, position.addition(v));
	}

	@Override
	public String toString() {
		return "Point2D{" + "origine=" + origine + ", position=" + position + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Point2D point2D = (Point2D) o;
		return Objects.equals(origine, point2D.origine) && Objects.equals(position, point2D.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origine, position);
	}

	@Override
	public Point2D clone() {
		try {
			return (Point2D) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
