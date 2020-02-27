/**
 * 
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

/**
 * Cette classe abstraite est une forme géometrique. Attribut de tous les
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
	 * Crée une Forme à partir de son centre qui est un Point2D.
	 * 
	 * @param p Le point qui deviendra le centre de la forme.
	 */
	public Forme(Point2D p) {
		centre = p;
	}

	/**
	 * Crée une Forme à partir des coordonnées de son centre.
	 * 
	 * @param x Abscisse du centre.
	 * @param y Ordonnée du centre.
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
	 * Permet de savoir la Forme est supperposée avec l'autre forme {@code f}.
	 * 
	 * @param f Forme avec laquelle il fut tester la superposition.
	 * @return true si les formes sont superposées, false sinon.
	 */
	public abstract boolean estSupperposee(Forme f) throws NoIntersectionException;// {
		// throw new UnsupportedOperationException();
		/*if (this.getClass() == Cercle.class) {
			return gereCercleSuperposition(f);
		}
		if (this.getClass() == Rectangle.class) {
			
		}
		return false;
	}*/

	private boolean gereCercleSuperposition(Forme f) {
		Cercle c = (Cercle) this;
		if (f.getClass() == Cercle.class) {
			Cercle c2 = (Cercle) f;
			return centre.getPositionRelative(f.centre).norme() < c.rayon + c2.rayon;
		}
		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				s.intersecte(c);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == Rectangle.class) {
			Polygone pol = ((Rectangle) f).toPolygone();
			for (Segment s : pol.getSegments()) {
				try {
					s.intersecte(c);
					return true;
				} catch (NoIntersectionException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		if (f.getClass() == ArcDeCercle.class) {
			throw new UnsupportedOperationException();
		}
		if (f.getClass() == Polygone.class) {
			Polygone pol = (Polygone) f;
			for (Segment s : pol.getSegments()) {
				try {
					s.intersecte(c);
					return true;
				} catch (NoIntersectionException e) {
					e.printStackTrace();
				}
			}
			return false;
		}		
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtient une nouvelle forme identique à l'initiale tournée de alpha (en
	 * radian).
	 * <p>
	 * Note: L'instance n'est pas modifiée.
	 * 
	 * @param alpha Angle de rotation en radian.
	 * @return La forme tournée de alpha dans une nouvelle instance.
	 */
	public Forme rotation(double alpha) {
		return rotation(alpha, centre);
	}

	/**
	 * Obtient une nouvelle forme identique à l'originale tournée de alpha (en
	 * radian) autour du point2D p.
	 * <p>
	 * Note: L'instance n'est pas modifiée.
	 * 
	 * @param alpha Angle de rotation en radian.
	 * @param p     Point2D centre de la rotation.
	 * @return Résultat de la rotation dans une nouvelle instance.
	 */
	public abstract Forme rotation(double alpha, Point2D p);

	/**
	 * Obtient le Rectangle encadrant la forme.
	 * 
	 * @return Rectangle encadrant la forme.
	 */
	public abstract Rectangle getDimension();
}
