package fr.rob4.simulation.exception;

import fr.rob4.simulation.geometrie.Point2D;

@SuppressWarnings("serial")
public class NonRelatifException extends Exception {
    private Point2D point;

    /**
     * Crée l'exception à partir du point en coordonnées absolues
     * 
     * @param p Le point en coordonnées absolues
     */
    public NonRelatifException(Point2D p) {
	super();
	point = p;
    }

    /**
     * Crée l'exception à partir du point en coordonnées absolues et d'un
     * message
     * 
     * @param p   Le point en coordonnées absolues
     * @param msg Un message décrivant l'erreur
     */
    public NonRelatifException(Point2D p, String msg) {
	super(msg);
	point = p;
    }

    /**
     * Obtient le point en coordonnées absolues
     * 
     * @return Le point en coordonnées absolues
     */
    public Point2D getPoint() {
	return point;
    }
}
