package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author flore
 *
 */
public class Polygone extends Forme {

	// Attributs
	protected List<Point2D> points;

	/**
	 * @param p
	 */
	public Polygone(Point2D p, Collection<Point2D> cp) {
		super(p);
		Iterator<Point2D> it = cp.iterator();
		while (it.hasNext()) {
			points.add(it.next());
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public Polygone(double x, double y, Collection<Point2D> cp) {
		super(x, y);
		Iterator<Point2D> it = cp.iterator();
		while (it.hasNext()) {
			points.add(it.next());
		}
	}

	public List<Point2D> getPoints() {
		return points;
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

}
