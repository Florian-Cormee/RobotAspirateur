package fr.rob4.simulation;

import fr.rob4.simulation.geometrie.Vecteur2D;

/**
 * Represente la position (coordonnees et orientation) d'un robot a deux roues
 */
public class Position implements Cloneable {
	protected double theta;
	protected Vecteur2D coord;

	/**
	 * Constructeur
	 */
	/**
	 * Cree une nouvelle posture
	 * 
	 * @param x     Coordonnee x initiale
	 * @param y     Coordonnee y initiale
	 * @param theta Angle initial
	 */
	public Position(double x, double y, double theta) {
		/*
		 * this.x = x; this.y = y;
		 */
		coord = new Vecteur2D(x, y);
		this.theta = theta;
	}

	// Accesseurs
	public double getX() {
		return coord.getX();
	}

	public double getY() {
		return coord.getY();
	}

	public double getTheta() {
		return theta;
	}

	public Object clone() {
		return new Position(coord.getX(), coord.getY(), theta);
	}

	/**
	 * Fait tourner une posture autour de l'origine du repere
	 * 
	 * @param alpha Angle de la rotation
	 */
	public Position rotate(double alpha) {
		double x_ = Math.cos(alpha) * getX() - Math.sin(alpha) * getY();
		double y_ = Math.cos(alpha) * getY() + Math.sin(alpha) * getX();
		x = x_;
		y = y_;
		theta = normalize_angle(alpha + theta);
		return this;
	}

	/**
	 * Deplace un robot en fonction de la distance parcourue par chaque roue
	 * 
	 * @param d_l        distance parcourue par la roue gauche
	 * @param d_r        distance parcourue par la roue droite
	 * @param ecartRoues distance entre les deux roues
	 */
	public Position move(double d_l, double d_r, double ecartRoues) {
		double alpha = (d_r - d_l) / ecartRoues;
		Position p = null;
		if (alpha > 1e-20 || alpha < -1e-20) {
			double r = (d_l / alpha) + ecartRoues / 2;
			double d_x = (Math.cos(alpha) - 1) * r;
			double d_y = Math.sin(alpha) * r;
			p = new Position(d_x, d_y, alpha);
			p.rotate(this.getTheta() - Math.PI / 2);
			p.theta = alpha;
		} else {
			p = new Position(d_l * Math.cos(this.getTheta()), d_l * Math.sin(this.getTheta()), 0);
		}

		this.x += p.x;
		this.y += p.y;
		this.theta = normalize_angle(p.theta + this.theta);

		return this;
	}

	/**
	 * Methode toString: permet d'afficher la posture
	 */
	public String toString() {
		return x + " " + y + " " + theta;
	}

	/**
	 * Convertit un angle pour qu'il soit dans [-PI;PI] (et non dans [0;2*PI] ou
	 * ailleurs)
	 * 
	 * @param a Angle a convertir
	 * @return L'angle a remis dans [-PI;PI]
	 */
	protected double normalize_angle(double a) {
		while (a > Math.PI)
			a -= 2 * Math.PI;
		while (a < -Math.PI)
			a += 2 * Math.PI;
		return a;
	}

	// Exemple d'utilisation
	public static void main(String args[]) {
		double largeur_robot = 34.0; // Le robot mesure 34cm de large
		Position p = new Position(0, 0, 0); // Nouvelle posture a l'origine
		for (int j = 0; j < 4; ++j) {
			for (int i = 0; i < 10; ++i) {
				System.out.println(p);
				// Fait tourner le robot vers la droite (la roue gauche avance
				// de 5.34cm et la droite reste immobile)
				p.move(5.34, 0, largeur_robot);
			}
			for (int i = 0; i < 10; ++i) {
				System.out.println(p);
				// Fait avancer le robot en ligne droite (les deux roues avancnt
				// de 5cm)
				p.move(5, 5, largeur_robot);
			}
		}
	}
}
