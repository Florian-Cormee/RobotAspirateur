package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Cette Classe représente un polygone. Il peut avoir autant de point que
 * souhaité.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 *
 */
public class Polygone extends Forme {

	// Attributs
	protected List<Point2D> points;

	/**
	 * Crée un polygone à partir de son centre et d'une liste de Point2D
	 * représentant ses sommets.
	 * 
	 * @param p  Centre
	 * @param cp Liste des sommets (Collection)
	 */
	public Polygone(Point2D p, Collection<Point2D> cp) {
		super(p);
		Iterator<Point2D> it = cp.iterator();
		while (it.hasNext()) {
			points.add(it.next());
		}
	}

	/**
	 * Crée un polygone à partir des coordonnées de son centre et d'une liste de
	 * Point2D représentant ses sommets.
	 * 
	 * @param x Abscisse du centre.
	 * @param y Ordonnée du centre.
	 * @param cp Liste des sommets (Collection)
	 */
	public Polygone(double x, double y, Collection<Point2D> cp) {
		super(x, y);
		Iterator<Point2D> it = cp.iterator();
		while (it.hasNext()) {
			points.add(it.next());
		}
	}

	/**
	 * Obtient la liste des sommets.
	 * 
	 * @return Liste des sommets.
	 */
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
