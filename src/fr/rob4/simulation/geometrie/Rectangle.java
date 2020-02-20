package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flore
 *
 */
public class Rectangle extends Forme {

	// Attributs
	protected double longueur;
	protected double largeur;

	/**
	 * @param p
	 */
	public Rectangle(Point2D p, double lon, double lar) {
		super(p);
		longueur = lon;
		largeur = lar;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Rectangle(double x, double y, double lon, double lar) {
		super(x, y);
		longueur = lon;
		largeur = lar;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	public double getLongueur() {
		return longueur;
	}

	public double getLargeur() {
		return largeur;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
	 * fr.rob4.simulation.geometrie.Point2D)
	 */
	@Override
	public Polygone rotation(double alpha, Point2D p) {
		List<Point2D> pointsRect = new ArrayList<Point2D>();
		double lon2 = longueur / 2;
		double lar2 = largeur / 2;
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, -lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, -lar2))));
		return new Polygone(centre, pointsRect).rotation(alpha, p);
	}

}
