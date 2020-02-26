package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un rectangle. Il connait sa longueur et sa largeur.
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
	protected double longueur;
	protected double largeur;

	/**
	 * Crée un rectangle à partir de son centre, de sa longueur et de sa largeur.
	 * 
	 * @param p   Centre
	 * @param lon Longeur
	 * @param lar Largeur
	 */
	public Rectangle(Point2D p, double lon, double lar) {
		super(p);
		longueur = lon;
		largeur = lar;
	}

	/**
	 * Crée un rectangle à partir des coordonnées de son centre, de sa longueur et
	 * de sa largeur.
	 * 
	 * @param x   Abscisse du centre
	 * @param y   Ordonnée du centre
	 * @param lon Longeur
	 * @param lar Largeur
	 */
	public Rectangle(double x, double y, double lon, double lar) {
		super(x, y);
		longueur = lon;
		largeur = lar;
	}

	/**
	 * Modifie la longeur du rectangle.
	 *
	 * @param longueur Nouvelle longeur.
	 */
	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	/**
	 * Modifie la largeur du rectangle.
	 * 
	 * @param largeur Nouvelle largeur.
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	/**
	 * Obtient la longeur.
	 * 
	 * @return Longueur
	 */
	public double getLongueur() {
		return longueur;
	}

	/**
	 * Obtient la largeur.
	 * 
	 * @return Largeur
	 */
	public double getLargeur() {
		return largeur;
	}

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
