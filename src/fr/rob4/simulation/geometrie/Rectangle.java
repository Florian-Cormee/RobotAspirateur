package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe représente un rectangle. Il connait sa largeur et sa hauteur.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Polygone
 */
public class Rectangle extends Forme {

	// Attributs
	protected double largeur;
	protected double hauteur;

	/**
	 * Crée un rectangle à partir de son centre, de sa largeur et de sa hauteur.
	 *
	 * @param p   Centre
	 * @param lar Largeur
	 * @param h   hauteur
	 */
	public Rectangle(Point2D p, double lar, double h) {
		super(p);
		this.largeur = lar;
		this.hauteur = h;
	}

	/**
	 * Crée un rectangle à partir des coordonnées de son centre, de sa largeur et de
	 * sa hauteur.
	 *
	 * @param x   Abscisse du centre
	 * @param y   Ordonnée du centre
	 * @param lar Largeur
	 * @param h   hauteur
	 */
	public Rectangle(double x, double y, double lar, double h) {
		super(x, y);
		this.largeur = Math.abs(lar);
		this.hauteur = Math.abs(h);
	}

	@Override
	public boolean collisionne(Forme f) throws NoIntersectionException {
		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				s.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == Cercle.class) {
			Cercle c = (Cercle) f;
			try {
				c.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == ArcDeCercle.class) {
			ArcDeCercle adc = (ArcDeCercle) f;
			try {
				adc.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == Polygone.class) {
			Polygone pol = (Polygone) f;
			try {
				pol.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == Rectangle.class) {
			Rectangle r = (Rectangle) f;
			try {
				this.intersecte(r);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		throw new NoIntersectionException(this, "La forme n'est pas connue.");

	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de rectangle et un
	 * autre rectangle mis en argument.
	 *
	 * @param r Rectangle avec lequel on teste l'intersection.
	 *
	 * @return Liste des points d'intersection.
	 *
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		try {
			return this.toPolygone().intersecte(r);
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException("Pas d'intersection entre ces deux rectangles.", e, this);
		}
	}

	@Override
	public Rectangle getDimension() {
		return this;
	}
	
	@Override
	public Polygone rotation(double alpha) {
		return this.rotation(alpha, centre);
	}

	@Override
	public Polygone rotation(double alpha, Point2D p) {

		return this.toPolygone().rotation(alpha, p);
	}

	@Override
	public Rectangle deplace(Vecteur2D v) {
		return new Rectangle(getCentre().deplace(v), largeur, hauteur);
	}

	/**
	 * Obtient l'equivalent du rectangle en polygone.
	 * <p>
	 * Note: Ne modifie pas l'instance.
	 * 
	 * @return Un Polygone avec les mêmes caractéristiques que le rectangle d'origine.
	 */
	public Polygone toPolygone() {
		List<Point2D> pointsRect = new ArrayList<Point2D>();
		double lon2 = this.largeur / 2;
		double lar2 = this.hauteur / 2;
		/*pointsRect.add(new Point2D(this.centre.getPositionAbsolue().addition(new Vecteur2D(-lon2, -lar2))));
		pointsRect.add(new Point2D(this.centre.getPositionAbsolue().addition(new Vecteur2D(-lon2, lar2))));
		pointsRect.add(new Point2D(this.centre.getPositionAbsolue().addition(new Vecteur2D(lon2, lar2))));
		pointsRect.add(new Point2D(this.centre.getPositionAbsolue().addition(new Vecteur2D(lon2, -lar2))));*/
		pointsRect.add(this.centre.deplace(new Vecteur2D(-lon2, -lar2)));
		pointsRect.add(this.centre.deplace(new Vecteur2D(-lon2, lar2)));
		pointsRect.add(this.centre.deplace(new Vecteur2D(lon2, lar2)));
		pointsRect.add(this.centre.deplace(new Vecteur2D(lon2, -lar2)));
		return new Polygone(pointsRect);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Rectangle rectangle = (Rectangle) o;
		return Objects.equals(this.centre, rectangle.centre) && (this.largeur == rectangle.largeur)
				&& (this.hauteur == rectangle.hauteur);
	}

	@Override
	public String toString() {
		return "Rectangle [largeur=" + this.largeur + ", hauteur=" + this.hauteur + ", centre=" + this.centre + "]";
	}

	/**
	 * Obtient la hauteur.
	 *
	 * @return hauteur
	 */
	public double getHauteur() {
		return this.hauteur;
	}

	/**
	 * Modifie la hauteur du rectangle.
	 *
	 * @param hauteur Nouvelle hauteur.
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * Obtient la longeur.
	 *
	 * @return largeur
	 */
	public double getLargeur() {
		return this.largeur;
	}

	/**
	 * Modifie la longeur du rectangle.
	 *
	 * @param largeur Nouvelle longeur.
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}
}
