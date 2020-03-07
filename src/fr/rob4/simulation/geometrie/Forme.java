/**
 *
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

/**
 * Cette classe abstraite est une forme géometrique. Attribut de tous les
 * elements de la simulation.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 * @see Polygone
 */
public abstract class Forme {

    // Attribut
    protected Point2D centre;

    /**
     * Crée une Forme à partir de son centre qui est un Point2D.
     *
     * @param p Le point qui deviendra le centre de la forme.
     */
    public Forme(Point2D p) {
		this.centre = p;
    }

    /**
     * Crée une Forme à partir des coordonnées de son centre.
     *
     * @param x Abscisse du centre.
     * @param y Ordonnée du centre.
     */
    public Forme(double x, double y) {
		this.centre = new Point2D(new Vecteur2D(x, y));
    }

    /**
     * Permet de savoir la Forme est supperposée avec l'autre forme {@code f}.
     *
     * @param f Forme avec laquelle il fut tester la superposition.
     * @return true si les formes sont superposées, false sinon.
     */
    public abstract boolean collisionne(Forme f) throws NoIntersectionException;

    /**
     * Obtient le centre.
     *
     * @return Le centre (Point2D)
     */
    public Point2D getCentre() {
        return this.centre;
    }

    /**
     * Obtient le Rectangle encadrant la forme.
     *
     * @return Rectangle encadrant la forme.
     */
    public abstract Rectangle getDimension();

    /**
     * Obtient une nouvelle forme identique à l'initiale tournée de alpha (en
     * radian).
     * <p>
     * Note: L'instance n'est pas modifiée.
     *
     * @param alpha Angle de rotation en radian.
     * @return La forme tournée de alpha dans une nouvelle instance.
     */
    public Forme rotation(double alpha) {
        return this.rotation(alpha, this.centre);
    }

    /**
     * Obtient une nouvelle forme identique à l'originale tournée de alpha (en
     * radian) autour du point2D p.
     * <p>
     * Note: L'instance n'est pas modifiée.
     *
     * @param alpha Angle de rotation en radian.
     * @param p     Point2D centre de la rotation.
     * @return Résultat de la rotation dans une nouvelle instance.
     */
    public abstract Forme rotation(double alpha, Point2D p);
    
    /**
     * Obtient une nouvelle forme identique à l'instance déplacé d'un vecteur v;
     * <p>
     * Note: L'instance n'est pas modifiée.
     * 
     * @param v Vecteur de déplacement.
     * @return Résultat du mouvement de translation par le vecteur v.
     */
    public abstract Forme deplace(Vecteur2D v);
}
