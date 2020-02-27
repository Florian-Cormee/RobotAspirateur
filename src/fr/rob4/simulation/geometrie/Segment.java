/**
 * 
 */
package fr.rob4.simulation.geometrie;

import java.util.List;
import java.util.ArrayList;

import fr.rob4.simulation.exception.NoIntersectionException;

/**
 * Cette classe représente un segment de droite délimité par 2 Point2D. Il peut
 * également être manipulé à partir de son centre.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
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
	 * 
	 * @param a
	 * @param b
	 */
	public Segment(Point2D a, Point2D b) {
		// double x = (a.getPositionAbsolue().x + b.getPositionAbsolue().x) / 2;
		// double y = (a.getPositionAbsolue().y + b.getPositionAbsolue().y) / 2;
		// centre = new Point2D(new Vecteur2D(x, y));
		super(new Point2D(new Vecteur2D((a.getPositionAbsolue().x + b.getPositionAbsolue().x) / 2,
				(a.getPositionAbsolue().y + b.getPositionAbsolue().y) / 2)));
		this.a = a;
		this.b = b;
	}

	/*
	 * /**
	 * 
	 * @param x
	 * 
	 * @param y / public Segment(double x, double y) { super(x, y); // TODO
	 * Auto-generated constructor stub }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
	 * fr.rob4.simulation.geometrie.Point2D)
	 */
	@Override
	public Segment rotation(double alpha, Point2D p) {
		return new Segment(a.rotation(alpha, p), b.rotation(alpha, p));
	}

	@Override
	public boolean estSuperposee(Forme f) throws NoIntersectionException {
		if (f.getClass() == Segment.class) {
			Segment s = (Segment) f;
			try {
				intersecte(s);
				return true;
			} catch (NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		if (f.getClass() == Cercle.class) {
			Cercle c = (Cercle) f;
			try {
				intersecte(c);
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
		if (f.getClass() == ArcDeCercle.class) {
			ArcDeCercle adc = (ArcDeCercle) f;
			try {
				intersecte(adc);
				return true;
			}catch( NoIntersectionException e) {
				e.printStackTrace();
				return false;
			}
		}
		throw new NoIntersectionException(this, "Ce segment n'a pas de collisions");
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * autre segment mis en argument.
	 * 
	 * @param seg Segment avec lequel on teste les intersections
	 * @return Point d'intersection.
	 * @throws NoIntersectionException
	 */
	Point2D intersecte(Segment seg) throws NoIntersectionException {
		Vecteur2D p = a.getPositionAbsolue();
		Vecteur2D q = seg.a.getPositionAbsolue();
		Vecteur2D r = a.getPositionRelative(b);
		Vecteur2D s = seg.a.getPositionRelative(seg.b);

		Vecteur2D tempV = q.soustraction(p);
		double tempD = r.produitCroix(s);
		if (tempD == 0) { // les segments sont soi parallèles soi en contacte mais ne se coupent pas.
			throw new NoIntersectionException(this,
					"les segments sont soi parallèles soi en contacte mais ne se coupent pas.");
		} else {
			double t = tempV.produitCroix(s) / tempD;
			double u = tempV.produitCroix(r) / tempD;
			if ((t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) { // Les 2 segments se croisent
				return new Point2D(p.addition(r.produit(t)));
			} else {
				throw new NoIntersectionException(this, "L\'intersection ne se fait sur les segments");
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
		double dx = a.getPositionAbsolue().x - b.getPositionAbsolue().x;
		double dy = a.getPositionAbsolue().y - b.getPositionAbsolue().y;
		double xc = c.centre.getPositionAbsolue().x;
		double yc = c.centre.getPositionAbsolue().y;

		double alpha = Math.pow(dx, 2) + Math.pow(dy, 2);
		double beta = 2 * (dx * (a.getPositionAbsolue().x - xc) + dy * (a.getPositionAbsolue().y - yc));
		double gamma = Math.pow(a.getPositionAbsolue().x - xc, 2) + Math.pow(a.getPositionAbsolue().y - yc, 2)
				+ Math.pow(c.rayon, 2);
		double delta = Math.pow(beta, 2) - 4 * alpha * gamma;
		// Conjecture
		if (delta == 0) { // le segment et est tangent au cercle
			throw new NoIntersectionException(this, "le segment est tangent au cercle.");
		} else if (delta > 0 && alpha != 0) {
			double t1 = (beta - Math.sqrt(delta)) / (2 * alpha);
			double t2 = (beta + Math.sqrt(delta)) / (2 * alpha);
			if (t1 >= 0 && t1 <= 1) {
				liste.add(new Point2D(a.getPositionAbsolue().addition(a.getPositionRelative(b).produit(t1))));
			}
			if (t2 >= 0 && t2 <= 1) {
				liste.add(new Point2D(a.getPositionAbsolue().addition(a.getPositionRelative(b).produit(t2))));
			}
			if (liste.size() == 0) {
				throw new NoIntersectionException(this, "L\'intersection ne se fait pas sur le segment.");
			} else {
				return liste;
			}
		}

		throw new NoIntersectionException(this, "Il n\'y a pas d\'intersection.");
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
		List<Point2D> liste = new ArrayList<Point2D>();
		for (Segment s : pol.getSegments()) {
			try {
				liste.add(s.intersecte(this));
			} catch (NoIntersectionException e) {
				continue;
			}
		}
		if (liste.size() == 0) {
			throw new NoIntersectionException(this, "Pas d'intersection entre le segment et le polygone");
		}
		return liste;
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
			List<Point2D> liste = intersecte(r.toPolygone());
			return liste;
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException(this, "Pas d'intersection entre le segment et le rectangle");
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance de segment et un
	 * arc de cercle mis en argument.
	 * 
	 * @param adc Arc de cercle avec lequel on teste les intersections.
	 * @return Liste des points d'intersection.
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(ArcDeCercle adc) throws NoIntersectionException {
		try {
			List<Point2D> liste = intersecte(new Cercle(adc.centre, adc.rayon));
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
						"L'intersection entre le segment et l'arc de cercle ne se fait pas sur l'arc de cercle.");
			} else {
				return liste;
			}
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException(this, "Pas d'intersection entre le segment et le rectangle");
		}
	}

	/**
	 * Obtient la distance/norme du segment.
	 * 
	 * @return La norme.
	 */
	public double norme() {
		return a.getPositionRelative(b).norme();
	}

	@Override
	public Rectangle getDimension() {
		Vecteur2D pa = a.getPositionAbsolue();
		Vecteur2D pb = b.getPositionAbsolue();
		double lar = Math.max(pa.x, pb.x) - Math.min(pa.x, pb.x);
		double h = Math.max(pa.y, pb.y) - Math.min(pa.y, pb.y);
		return new Rectangle(centre, lar, h);
	}

	public Point2D getA() {
		return a;
	}

	public Point2D getB() {
		return b;
	}

}
