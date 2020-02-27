/**
 * 
 */
package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.List;

import fr.rob4.simulation.Outil;
import fr.rob4.simulation.exception.NoIntersectionException;

/**
 * Cette classe représente un arc de cercle. C'est une portion de cercle. On
 * peut le manipuler grâce à 2 angles, la bisectrice de ces angles définissant
 * son orientation et son ouverture, la différence de ces 2 angles. Classe fille
 * de Cercle.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see Rectangle
 * @see Polygone
 */
public class ArcDeCercle extends Cercle {

	// Attributs
	protected double ang1;
	protected double ang2;

	/**
	 * Crée un arc de cercle à partir de son centre (Point2D), son diametre, et les
	 * 2 angles par rapport à l'axe des abscisses x, définissant son ouverture et
	 * son orientation.
	 * 
	 * @param p  Centre
	 * @param d  Diametre
	 * @param a1 1er angle
	 * @param a2 2eme angle
	 */
	public ArcDeCercle(Point2D p, double d, double a1, double a2) {
		super(p, d);
		ang1 = Outil.normalize_angle(a1);
		ang2 = Outil.normalize_angle(a2);
	}

	/**
	 * Crée un arc de cecle à partir des coordonnées de son centre, son diametre, et
	 * les 2 angles par rapport à l'axe des abscisses, définissant son ouverture et
	 * son orientatioon.
	 * 
	 * @param x  Abscisse du centre
	 * @param y  Ordonnée du centre
	 * @param d  Diametre
	 * @param a1 1er angle
	 * @param a2 2eme angle
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

	/**
	 * Obtient l'ouverture de l'arc de cercle.
	 * 
	 * @return Ouverture, un angle.
	 */
	public double getOuverture() {
		if (ang1 > ang2) {
			return Math.PI * 2 - ang1 + ang2;
		}
		return ang2 - ang1;
	}

	/**
	 * Obtient l'orientation de l'arc de cercle.
	 * 
	 * @return Orientation, un angle.
	 */
	public double getOrientation() {
		if (ang2 > ang1) {
			return getOuverture() / 2;
		}
		return Outil.normalize_angle((ang1 + ang2) / 2 + Math.PI);
	}

	/**
	 * Modifie l'ouverture avec un nouvel angle d'ouverture.
	 * 
	 * @param o Nouvelle ouverture.
	 */
	public void setOuverture(double o) {
		double mid = o / 2; // la moitié de la nouvelle ouverture
		// On modifie les angles pour avoir la bonne ouverture autour de l'orientation
		// actuelle
		ang1 = getOrientation() + mid;
		ang2 = getOrientation() - mid;
	}

	/**
	 * Modifie l'orientation avec un nouvel angle.
	 * 
	 * @param o Orientation, un angle.
	 */
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

	@Override
	public boolean estSuperposee(Forme f) throws NoIntersectionException {
		// On teste d'abord si les formes sont assez proches
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
		if (f.getClass() == Polygone.class) {
			Polygone p = (Polygone) f;
			try {
				intersecte(p);
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
		if (f.getClass() == ArcDeCercle.class) {
			ArcDeCercle adc = (ArcDeCercle) f;
			try {
				intersecte(adc);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		throw new NoIntersectionException(this,
				"Cet arc de cercle n'a pas de collision. Ou la forme n'est pas connue.");
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance d'arc de cercle et
	 * un polygone mis en argument.
	 * 
	 * @param pol Polygone avec lequel on teste les intersections.
	 * @return Liste des points d'intersection
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
		List<Point2D> liste = new ArrayList<Point2D>();
		for (Segment s : pol.getSegments()) {
			liste.addAll(s.intersecte(this));
		}
		if (liste.size() == 0) {
			throw new NoIntersectionException(this, "Pas d'intersection entre cet arc de cercle et le polygone.");
		} else {
			return liste;
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance d'arc de cercle et
	 * un rectangle mis en argument.
	 * 
	 * @param r Rectangle avec lequel on teste les intersections
	 * @return Liste des points d'intersection
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		try {
			List<Point2D> liste = intersecte(r.toPolygone());
			return liste;
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException(this, "Pas d'intersection entre cet arc de cercle et le rectangle.");
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance d'arc de cercle et
	 * un arc de cercle mis en argument.
	 * 
	 * @param adc Arc de cercle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(ArcDeCercle adc) throws NoIntersectionException {
		try {
			List<Point2D> liste = new Cercle(adc.centre, adc.rayon).intersecte(this);
			Vecteur2D x = new Vecteur2D(1, 0);
			for (Point2D p : liste) { // on verifie pour tous les points s'ils sont dans le bon intervalle d'angles.
				Vecteur2D test = adc.centre.getPositionRelative(p);
				if (adc.ang1 <= adc.ang2) {
					if (test.angle(x) < adc.ang1 || test.angle(x) > adc.ang2) {
						liste.remove(p);
					}
				} else {
					if (test.angle(x) < adc.ang1 && test.angle(x) > adc.ang2) {
						liste.remove(p);
					}
				}
			}
			if (liste.size() == 0) { // si la liste est vide, c'est que les points n'étaient pas dans le bon
										// intervalle.
				throw new NoIntersectionException(this,
						"L'intersection entre l'instance d'arc de cercle et l'autre arc de cercle ne se fait pas sur l'arc de cercle.");
			} else {
				return liste;
			}
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException(this,
					"Pas d'intersection entre cet arc de cercle et l'autre arc de cercle.");
		}
	}
}
