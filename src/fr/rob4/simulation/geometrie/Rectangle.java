package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.rob4.simulation.exception.NoIntersectionException;

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
		largeur = lar;
		hauteur = h;
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
		largeur = lar;
		hauteur = h;
	}

	/**
	 * Modifie la longeur du rectangle.
	 *
	 * @param largeur Nouvelle longeur.
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
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
		return largeur;
	}

	/**
	 * Obtient la hauteur.
	 * 
	 * @return hauteur
	 */
	public double getHauteur() {
		return hauteur;
	}

	@Override
	public Polygone rotation(double alpha, Point2D p) {

		return toPolygone().rotation(alpha, p);
	}

	@Override
	public Rectangle getDimension() {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Rectangle rectangle = (Rectangle) o;
		return Objects.equals(centre, rectangle.centre) && (largeur == rectangle.largeur)
				&& (hauteur == rectangle.hauteur);
	}

	public Polygone toPolygone() {
		List<Point2D> pointsRect = new ArrayList<Point2D>();
		double lon2 = largeur / 2;
		double lar2 = hauteur / 2;
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, -lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, +lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, +lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, -lar2))));
		return new Polygone(centre, pointsRect);
	}

	@Override
	public boolean collisionne(Forme f) throws NoIntersectionException {
		// On test d'abord si les formes sont assez proches
		try {
			getDimension().intersecte(f.getDimension());
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			return false;
		}

		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				s.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == Cercle.class) {
			Cercle c = (Cercle) f;
			try {
				c.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == ArcDeCercle.class) {
			ArcDeCercle adc = (ArcDeCercle) f;
			try {
				adc.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == Polygone.class) {
			Polygone pol = (Polygone) f;
			try {
				pol.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == Rectangle.class) {
			Rectangle r = (Rectangle) f;
			try {
				intersecte(r);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		throw new NoIntersectionException(this, "Ce rectangle n'a pas de collision. Ou la forme n'est pas connue.");

	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de rectangle et un
	 * autre rectangle mis en argument.
	 * 
	 * @param r Rectangle avec lequel on teste l'intersection.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		try {
			return this.toPolygone().intersecte(r);
		} catch (NoIntersectionException e) {
			e.printStackTrace();
			throw new NoIntersectionException(this, "Pas d'intersection entre ces deux rectangles.");
		}
	}
}
