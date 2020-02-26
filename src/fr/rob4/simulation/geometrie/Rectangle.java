package fr.rob4.simulation.geometrie;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un rectangle. Il connait sa largeur et sa hauteur.
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
	protected double largeur;
	protected double hauteur;

	/**
	 * Crée un rectangle à partir de son centre, de sa largeur et de sa hauteur.
	 * 
	 * @param p   Centre
	 * @param lar Largeur
	 * @param h   hauteur
	 */
	public Rectangle(Point2D p, double lar, double h) {
		super(p);
		largeur = lar;
		hauteur = h;
	}

	/**
	 * Crée un rectangle à partir des coordonnées de son centre, de sa largeur et de
	 * sa hauteur.
	 * 
	 * @param x   Abscisse du centre
	 * @param y   Ordonnée du centre
	 * @param lar Largeur
	 * @param h   hauteur
	 */
	public Rectangle(double x, double y, double lar, double h) {
		super(x, y);
		largeur = lar;
		hauteur = h;
	}

	/**
	 * Modifie la longeur du rectangle.
	 *
	 * @param largeur Nouvelle longeur.
	 */
	public void setLargeur(double largeur) {
		this.largeur = largeur;
	}

	/**
	 * Modifie la hauteur du rectangle.
	 * 
	 * @param hauteur Nouvelle hauteur.
	 */
	public void setHauteur(double hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * Obtient la longeur.
	 * 
	 * @return largeur
	 */
	public double getLargeur() {
		return largeur;
	}

	/**
	 * Obtient la hauteur.
	 * 
	 * @return hauteur
	 */
	public double getHauteur() {
		return hauteur;
	}

	@Override
	public Polygone rotation(double alpha, Point2D p) {
		List<Point2D> pointsRect = new ArrayList<Point2D>();
		double lon2 = largeur / 2;
		double lar2 = hauteur / 2;
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(lon2, -lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, lar2))));
		pointsRect.add(new Point2D(centre.origine, centre.position.addition(new Vecteur2D(-lon2, -lar2))));
		return new Polygone(centre, pointsRect).rotation(alpha, p);
	}

	@Override
	public Rectangle getDimension() {
		return this;
	}
}
