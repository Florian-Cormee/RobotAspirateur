package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Cette Classe représente un polygone. Il peut avoir autant de point que
 * souhaité.
 *
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 */
public class Polygone extends Forme {

	// Attributs
	protected List<Point2D> points;

	/**
	 * Crée un polygone à partir d'une liste de Point2D représentant ses sommets.
	 * 
	 * @param cp Liste des points du polygone
	 */
	public Polygone(Collection<? extends Point2D> cp) {
		super(Polygone.baricentre(cp));
		this.points = new ArrayList<>(cp);
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
			Polygone pol = (Polygone) f;
			try {
				this.intersecte(pol);
				return true;
			} catch (NoIntersectionException e) {
				return false;
			}
		}
		throw new NoIntersectionException(this, "La forme n'est pas connue.");
	}

	@Override
	public Rectangle getDimension() {
		double xMax = Double.NEGATIVE_INFINITY;
		double xMin = Double.POSITIVE_INFINITY;
		double yMax = Double.NEGATIVE_INFINITY;
		double yMin = Double.POSITIVE_INFINITY;

		for (Point2D p : this.points) {
			Vecteur2D vTemp = p.getPositionAbsolue();
			xMax = Math.max(xMax, vTemp.x);
			xMin = Math.min(xMin, vTemp.x);
			yMax = Math.max(yMax, vTemp.y);
			yMin = Math.min(yMin, vTemp.y);
		}

		return new Rectangle(this.centre, xMax - xMin, yMax - yMin);
	}

	@Override
	public Polygone rotation(double alpha) {
		return this.rotation(alpha, centre);
	}

	@Override
	public Polygone rotation(double alpha, Point2D p) {
		List<Point2D> newPoints = new ArrayList<>();
		// On fait touner tous les points du polygone par rapport au point demandé
		for (Point2D point : this.points) {
			Point2D newPt = point.rotation(alpha, p);
			newPoints.add(newPt);// new Point2D(point.origine, newPos));
		}
		return new Polygone(newPoints);
	}

	@Override
	public Polygone deplace(Vecteur2D v) {
		List<Point2D> newL = new ArrayList<Point2D>();
		// On ajoute à la nouvelle liste de points les points de l'instance déplacés du
		// vecteur demandé.
		for (Point2D p : getPoints()) {
			newL.add(p.deplace(v));
		}
		return new Polygone(newL);
	}

	/**
	 * Obtient la liste des sommets.
	 *
	 * @return Liste des sommets.
	 */
	public List<Point2D> getPoints() {
		return Collections.unmodifiableList(this.points);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		Polygone polygone = (Polygone) o;
		return Objects.deepEquals(this.points.toArray(), polygone.points.toArray())
				&& Objects.equals(this.centre, polygone.centre);
	}

	@Override
	public String toString() {
		return "Polygone [points=" + this.points + ", centre=" + this.centre + "]";
	}

	/**
	 * Obtient la liste des segments composants le polygone.
	 * 
	 * @return Liste des segments de l'instance
	 */
	public List<Segment> getSegments() {
		List<Segment> liste = new ArrayList<>();
		// On garde en mémoire le premier point pour construire le dernier segment et
		// fermer le polygone.
		Point2D prem = this.points.get(0);
		Point2D pt1 = prem;
		// Point2D pt2 = points.get(1);
		for (Point2D p : this.points) {
			if (p.equals(prem)) {
				continue;
			}
			liste.add(new Segment(pt1, p));
			pt1 = p;
		}
		liste.add(new Segment(pt1, prem));
		return liste;
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance d'un polygone et
	 * un rectangle mis en argument.
	 *
	 * @param r Rectangle avec lequel on teste l'intersection.
	 *
	 * @return Liste des points d'intersection.
	 *
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
		try {
			// On convertit le rectangle en polygone pour tester l'intersection entre les
			// segments.
			Polygone polygone = r.toPolygone();
			return this.intersecte(polygone);
		} catch (NoIntersectionException e) {
			throw new NoIntersectionException("Pas d'intersection entre le polygone et le rectangle", e, this);
		}
	}

	/**
	 * Obtient la liste de points d'intersection entre l'instance d'un polygone et
	 * un polygone mis en argument.
	 *
	 * @param pol Polygone avec lequel on test l'intersection.
	 *
	 * @return Liste des points d'intersection.
	 *
	 * @throws NoIntersectionException
	 */
	List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
		Set<Point2D> ensemble = new HashSet<Point2D>();
		// On teste avec tous les segments de l'instance avec le polygone à tester
		for (Segment s : this.getSegments()) {
			try {
				ensemble.addAll(s.intersecte(pol));
			} catch (NoIntersectionException e) {
			}
		}
		if (ensemble.size() != 0) {	// On vérifie que la taille de l'ensemble
			return new ArrayList<Point2D>(ensemble);
		} else {
			throw new NoIntersectionException(this, "Pas d'intersection entre ce polygone et l'autre.");
		}
	}

	/**
	 * Calcule le baricentre d'une liste de points.
	 * 
	 * @param cPts Liste de points dont il faut trouver le baricentre.
	 * @return Le baricentre.
	 */
	private static Point2D baricentre(Collection<? extends Point2D> cPts) {
		int nb = cPts.size();
		double xb = 0;
		double yb = 0;

		Iterator<? extends Point2D> iterator = cPts.iterator();
		Point2D p;
		while (iterator.hasNext()) {
			p = iterator.next();
			xb += p.getPositionAbsolue().getX();
			yb += p.getPositionAbsolue().getY();
		}
		
		// moyenne des points
		return new Point2D(new Vecteur2D(xb / nb, yb / nb));
	}
}
