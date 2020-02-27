package fr.rob4.simulation.geometrie;

import java.util.*;

/**
 * Cette Classe repr�sente un polygone. Il peut avoir autant de point que
 * souhait�.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
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
	 * Cr�e un polygone � partir de son centre et d'une liste de Point2D
	 * repr�sentant ses sommets.
	 *
	 * @param p  Centre
	 * @param cp Liste des sommets (Collection)
	 */
	public Polygone(Point2D p, Collection<Point2D> cp) {
		super(p);
		points = new ArrayList<Point2D>(cp);
	}

	/**
	 * Cr�e un polygone � partir des coordonn�es de son centre et d'une liste de
	 * Point2D repr�sentant ses sommets.
	 *
	 * @param x  Abscisse du centre.
	 * @param y  Ordonn�e du centre.
	 * @param cp Liste des sommets (Collection)
	 */
	public Polygone(double x, double y, Collection<Point2D> cp) {
		super(x, y);
		points = new ArrayList<Point2D>(cp);
	}

	/**
	 * Obtient la liste des sommets.
	 *
	 * @return Liste des sommets.
	 */
	public List<Point2D> getPoints() {
		return Collections.unmodifiableList(points);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
	 * fr.rob4.simulation.geometrie.Point2D)
	 */
	@Override
	public Polygone rotation(double alpha, Point2D p) {
		List<Point2D> newPoints = new ArrayList<Point2D>();
		Iterator<Point2D> it = points.iterator();
		while (it.hasNext()) {
			Point2D point = it.next();
			Vecteur2D newPos = p.getPositionRelative(point.origine).rotation(alpha).addition(p.position);
			newPoints.add(new Point2D(point.origine, newPos));
		}
		return new Polygone(centre.clone(), newPoints);
	}

	@Override
	public Rectangle getDimension() {
		double xMax = Double.NEGATIVE_INFINITY;
		double xMin = Double.POSITIVE_INFINITY;
		double yMax = Double.NEGATIVE_INFINITY;
		double yMin = Double.POSITIVE_INFINITY;

		for (Point2D p : points) {
			Vecteur2D vTemp = p.getPositionAbsolue();
			xMax = Math.max(xMax, vTemp.x);
			xMin = Math.min(xMin, vTemp.x);
			yMax = Math.max(yMax, vTemp.y);
			yMin = Math.min(yMin, vTemp.y);
		}

		return new Rectangle(centre, xMax - xMin, yMax - yMin);
	}
	
	public List<Segment> getSegments(){
		List<Segment> liste = new ArrayList<Segment>();
		Point2D prem = points.get(0);
		Point2D pt1 = prem;
		//Point2D pt2 = points.get(1);
		for( Point2D p : points) {
			if( p.equals(prem)) {
				continue;
			}
			liste.add(new Segment(pt1,p));
			pt1 = p;
		}
		liste.add(new Segment(pt1,prem));
		return liste;
	}
}
