/**
 *
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.Outil;
import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Cette classe représente un Cercle paramétré par son centre et son rayon.
 * Classe fille de Forme.
 *
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
	public Cercle(Point2D p, double r) {
		super(p);
		this.rayon = r;
	}

	/**
	 * Crée un nouveau cercle à partir des coordonnées du centre et du diamètre.
	 *
	 * @param x Abscisse du centre.
	 * @param y Ordonnée du centre.
	 * @param d rayon
	 */
	public Cercle(double x, double y, double r) {
		super(x, y);
		this.rayon = r;
	}

	@Override
	public boolean collisionne(Forme f) throws NoIntersectionException {
		if (f.getClass() == Cercle.class) {
			Cercle c = (Cercle) f;
			try {
				this.intersecte(c);
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
		if (f.getClass() == Polygone.class) {
			Polygone g = (Polygone) f;
			try {
				this.intersecte(g);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == ArcDeCercle.class) {
			ArcDeCercle adc = (ArcDeCercle) f;
			try {
				this.intersecte(adc);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				s.intersecte(this);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		throw new NoIntersectionException(this, "La forme n'est pas connue.");
	}

	@Override
	public Rectangle getDimension() {
		return new Rectangle(this.centre, this.rayon * 2, this.rayon * 2);
	}

	@Override
	public Cercle rotation(double alpha) {
		return this.rotation(alpha, centre);
	}

	@Override
	public Cercle rotation(double alpha, Point2D p) {
		Vecteur2D newPos = p.getPositionRelative(this.centre).rotation(alpha).addition(p.position);
		return new Cercle(new Point2D(this.centre.origine, newPos), this.rayon);
	}

	@Override
	public Cercle deplace(Vecteur2D v) {
		return new Cercle(getCentre().deplace(v), rayon);
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de cercle et un
	 * autre cercle mis en argument.
	 *
	 * @param cercle Cercle avec lequel on test les collisions.
	 * @return Liste de point2D étant des points d'interection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Cercle cercle) throws NoIntersectionException {
		double entraxe = centre.getPositionRelative(cercle.centre).norme(); // distance entre les centres des cercles
		if (entraxe >= rayon + cercle.rayon || entraxe < Math.abs(rayon - cercle.rayon) || entraxe == 0) {
			// les cercles sont tangents, l'un est inclu dans l'autre, ils sont
			// concentriques
			throw new NoIntersectionException(this, "Pas d'intersection entre les cercles");
		} else {
			List<Point2D> liste = new ArrayList<Point2D>();

			double rayonC = cercle.rayon;

			double a = (rayon * rayon - rayonC * rayonC + entraxe * entraxe) / (2 * entraxe); // distance entre le
																								// centre de l'instance
																								// et le point milieu de
																								// l'intersection entre
																								// les cercles.
			double h = Math.sqrt(rayon * rayon - a * a); // distance entre le point milieu et les points d'intersection
															// (symétrique des deux cotés du point milieu)

			double x0 = this.centre.getPositionAbsolue().getX();
			double y0 = this.centre.getPositionAbsolue().getY();
			double x1 = cercle.centre.getPositionAbsolue().getX();
			double y1 = cercle.centre.getPositionAbsolue().getY();

			Vecteur2D milieu = centre.getPositionAbsolue()
					.addition(centre.getPositionRelative(cercle.centre).normalise().produit(a));// cercle.centre.getPositionAbsolue();
			double x2 = milieu.getX();
			double y2 = milieu.getY();

			// Construction des deux points symétriques
			double xp = x2 + h * (y1 - y0) / entraxe;
			double yp = y2 - h * (x1 - x0) / entraxe;
			liste.add(new Point2D(new Vecteur2D(xp, yp)));

			double xq = x2 - h * (y1 - y0) / entraxe;
			double yq = y2 + h * (x1 - x0) / entraxe;
			liste.add(new Point2D(new Vecteur2D(xq, yq)));

			return liste;
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de cercle et un
	 * rectangle mis en argument.
	 *
	 * @param r Rectangle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		// On convertit le rectangle en polygone pour tester l'intersection entre les
		// segment et le cercle
		return this.intersecte(r.toPolygone());
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de cercle et un
	 * polygone mis en argument.
	 *
	 * @param pol Polygone avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
		Set<Point2D> ensemble = new HashSet<Point2D>();
		// On teste avec l'ensemble des segments du polygone pour utiliser
		// l'intersection entre un cercle et un segment
		for (Segment s : pol.getSegments()) {
			try {
				ensemble.addAll(s.intersecte(this));
			} catch (NoIntersectionException e) {
				continue;
			}
		}
		if (ensemble.size() == 0) {
			throw new NoIntersectionException(this, "Pas d'intersection entre le cercle et le rectangle");
		}
		return new ArrayList<Point2D>(ensemble);
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de cercle et un
	 * arc de cercle mis en argument.
	 *
	 * @param adc Arc de cercle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(ArcDeCercle adc) throws NoIntersectionException {
		try {
			List<Point2D> liste = this.intersecte(new Cercle(adc.centre, adc.rayon));
			// Vecteur unitaire horizontal pour tester l'angle
			Vecteur2D x = new Vecteur2D(1, 0);
			
			Iterator<Point2D> iterator = liste.iterator();
			// On verifie pour tous les points s'ils sont dans le bon intervalle d'angles.
			Point2D p;
			while (iterator.hasNext()) {
				p = iterator.next();
				Point2D centreAdc = adc.getCentre();
				Vecteur2D test = centreAdc.getPositionRelative(p);
				// Angle entre le point et l'orientation de l'arc de cercle
				double angle = Outil.normalize_angle(x.angle(test) - adc.getOrientation());
				double ouverture = adc.getOuverture();
				// Cette angle est dans l'intervalle d'ouverture
				if (-ouverture / 2 >= angle || angle >= ouverture / 2) {
					iterator.remove();
				}
			}
			
			if (liste.size() == 0) {	// On vérifie si la liste n'est pas vide
				throw new NoIntersectionException(this,
						"L'intersection entre le cercle et l'arc de cercle ne se fait pas " + "sur l'arc de cercle.");
			} else {
				return liste;
			}
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException("Pas d'intersection entre le cercle et l'arc de cercle", e, this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Cercle cercle = (Cercle) o;
		return Objects.equals(this.centre, cercle.centre) && (this.rayon == cercle.rayon);
	}

	@Override
	public String toString() {
		return "Cercle [rayon=" + this.rayon + ", centre=" + this.centre + "]";
	}

	/**
	 * Obtient le rayon
	 *
	 * @return Le rayon
	 */
	public double getRayon() {
		return this.rayon;
	}
}
