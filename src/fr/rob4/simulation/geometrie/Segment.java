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
import java.util.Set;

/**
 * Cette classe représente un segment de droite délimité par 2 Point2D. Il peut
 * également être manipulé à partir de son centre.
 *
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 * @see Polygone
 */
public class Segment extends Forme {

	// Attributs
	protected Point2D a;
	protected Point2D b;

	/**
	 * Crée un segment à partir des 2 points qui sont ses extrémitées.
	 * 
	 * @param a Première extrémitée
	 * @param b Deuxième extrémitée
	 */
	public Segment(Point2D a, Point2D b) {
		super(new Point2D(new Vecteur2D((a.getPositionAbsolue().x + b.getPositionAbsolue().x) / 2,
				(a.getPositionAbsolue().y + b.getPositionAbsolue().y) / 2)));
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean collisionne(Forme f) throws NoIntersectionException {
		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				this.intersecte(s);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
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
			Polygone p = (Polygone) f;
			try {
				this.intersecte(p);
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
		throw new NoIntersectionException(this, "La forme n'est pas connue.");
	}

	@Override
	public Rectangle getDimension() {
		Vecteur2D pa = this.a.getPositionAbsolue();
		Vecteur2D pb = this.b.getPositionAbsolue();
		double lar = Math.max(pa.x, pb.x) - Math.min(pa.x, pb.x);
		double h = Math.max(pa.y, pb.y) - Math.min(pa.y, pb.y);
		return new Rectangle(this.centre, lar, h);
	}

	@Override
	public Segment rotation(double alpha) {
		return this.rotation(alpha, centre);
	}

	@Override
	public Segment rotation(double alpha, Point2D p) {
		return new Segment(this.a.rotation(alpha, p), this.b.rotation(alpha, p));
	}

	@Override
	public Segment deplace(Vecteur2D v) {
		return new Segment(a.deplace(v), b.deplace(v));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Segment segment = (Segment) o;
		return this.a.getPositionAbsolue().equals(segment.a.getPositionAbsolue())
				&& this.b.getPositionAbsolue().equals(segment.b.getPositionAbsolue());
	}

	@Override
	public String toString() {
		return "Segment [a=" + this.a + ", b=" + this.b + ", centre=" + this.centre + "]";
	}

	/**
	 * Obtient le point de la première extrémitée.
	 * 
	 * @return Point de la première extrémitée.
	 */
	public Point2D getA() {
		return this.a;
	}

	/**
	 * Obtient le point de la deuxième extrémitée.
	 * 
	 * @return Point de la deuxième extrémitée.
	 */
	public Point2D getB() {
		return this.b;
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * autre segment mis en argument.
	 * <p>
	 * Note: Si les segment sont tangents, le point de contact est le centre de
	 * l'instance.
	 *
	 * @param seg Segment avec lequel on teste les intersections
	 * @return Point d'intersection.
	 * @throws NoIntersectionException
	 */
	Point2D intersecte(Segment seg) throws NoIntersectionException {
		Vecteur2D p = this.a.getPositionAbsolue();
		Vecteur2D q = seg.a.getPositionAbsolue();
		Vecteur2D r = this.a.getPositionRelative(this.b);
		Vecteur2D s = seg.a.getPositionRelative(seg.b);

		Vecteur2D tempV = q.soustraction(p);
		double tempD = r.produitCroix(s);
		if (tempD == 0) { // les segments sont soi parallèles soi en contacte mais ne se coupent pas.
			throw new NoIntersectionException(this,
					"les segments sont soi parallèles soi en contacte mais ne se coupent " + "pas.");
		} else {
			double t = tempV.produitCroix(s) / tempD; // proportion de r dans le segment this
			double u = tempV.produitCroix(r) / tempD; // proportion de s dans le segment à tester
			if ((t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) { // Les 2 segments se croisent
				return new Point2D(p.addition(r.produit(t)));
			} else {
				throw new NoIntersectionException(this, "L'intersection ne se fait sur les segments");
			}
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * cercle mis en argument.
	 *
	 * @param c Cercle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Cercle c) throws NoIntersectionException {
		List<Point2D> liste = new ArrayList<Point2D>();
		// On cherche à résoudre l'équation des représentations paramétriques du segment
		// this et du cercle.
		double dx = this.b.getPositionAbsolue().getX() - this.a.getPositionAbsolue().getX();
		double dy = this.b.getPositionAbsolue().getY() - this.a.getPositionAbsolue().getY();
		double xc = c.centre.getPositionAbsolue().getX();
		double yc = c.centre.getPositionAbsolue().getY();

		// Equation du 2nd ordre : alpha * x² + beta * x + c = 0
		double alpha = Math.pow(dx, 2) + Math.pow(dy, 2);
		double beta = 2
				* (dx * (this.a.getPositionAbsolue().getX() - xc) + dy * (this.a.getPositionAbsolue().getY() - yc));
		double gamma = Math.pow(this.a.getPositionAbsolue().getX() - xc, 2)
				+ Math.pow(this.a.getPositionAbsolue().getY() - yc, 2) - Math.pow(c.rayon, 2);

		double delta = Math.pow(beta, 2) - 4 * alpha * gamma;

		// Conjecture
		if (delta == 0) { // le segment et est tangent au cercle
			throw new NoIntersectionException(this, "le segment est tangent au cercle.");
		} else if (delta > 0 && alpha != 0) { // les racines ne sont pas complexes et éviter le division par 0
			double t1 = (-beta - Math.sqrt(delta)) / (2 * alpha);
			double t2 = (-beta + Math.sqrt(delta)) / (2 * alpha);
			if (t1 >= 0 && t1 <= 1) {
				liste.add(new Point2D(
						this.a.getPositionAbsolue().addition(this.a.getPositionRelative(this.b).produit(t1))));
			}
			if (t2 >= 0 && t2 <= 1) {
				liste.add(new Point2D(
						this.a.getPositionAbsolue().addition(this.a.getPositionRelative(this.b).produit(t2))));
			}
			if (liste.size() == 0) {
				throw new NoIntersectionException(this, "L'intersection ne se fait pas sur le segment.");
			} else {
				return liste;
			}
		}

		throw new NoIntersectionException(this, "Il n'y a pas d'intersection.");
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * polygone mis en argument.
	 *
	 * @param pol Polygone avec lequel on teste les intersections
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
		Set<Point2D> ensemble = new HashSet<Point2D>();
		// On test l'intersection entre this et tous les segments du polynome
		for (Segment s : pol.getSegments()) {
			try {
				// ensemble.addAll(s.intersecte(pol));
				Point2D ptColl = s.intersecte(this);
				ensemble.add(ptColl);

			} catch (NoIntersectionException e) {
				continue;
			}
		}
		if (ensemble.size() == 0) { // on vérifie si la liste est vide
			throw new NoIntersectionException(this, "Pas d'intersection entre le segment et le polygone");
		}
		return new ArrayList<Point2D>(ensemble);
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * rectangle mis en argument.
	 *
	 * @param r Rectangle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		try {
			// On convertit le rectangle polynome pour tester des intersections entre
			// segments.
			List<Point2D> liste = this.intersecte(r.toPolygone());
			return liste;
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException("Pas d'intersection entre le segment et le rectangle", e, this);
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * arc de cercle mis en argument.
	 *
	 * @param adc Arc de cercle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException Quand il n'y a pas d'intersection
	 */
	List<Point2D> intersecte(ArcDeCercle adc) throws NoIntersectionException {
		try {
			List<Point2D> liste = this.intersecte((Cercle) adc);
			Iterator<Point2D> iterator = liste.iterator();
			Vecteur2D x = new Vecteur2D(1, 0); // vecteur unitaire horizontal pour vérifier l'angle

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
			if (liste.size() == 0) { // si la liste est vide, c'est que les points n'étaient pas dans le bon
				// intervalle.
				throw new NoIntersectionException(this,
						"L'intersection entre le segment et l'arc de cercle ne se fait" + " pas sur l'arc de cercle.");
			} else {
				return liste;
			}
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException("Pas d'intersection entre le segment et l'arc de cercle.", e, this);
		}
	}

	/**
	 * Obtient la distance/norme du segment.
	 *
	 * @return La norme.
	 */
	public double norme() {
		return this.a.getPositionRelative(this.b).norme();
	}

}
