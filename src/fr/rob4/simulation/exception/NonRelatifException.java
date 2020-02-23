package fr.rob4.simulation.exception;

import fr.rob4.simulation.geometrie.Point2D;

@SuppressWarnings("serial")
public class NonRelatifException extends Exception {
    private Point2D point;

    /**
     * Cree l'exception a partir du point en coordonnees absolues
     * 
     * @param p Le point en coordonnees absolues
     */
    public NonRelatifException(Point2D p) {
	super();
	point = p;
    }

    /**
     * Cree l'exception a partir du point en coordonnees absolues et d'un
     * message
     * 
     * @param p   Le point en coordonnees absolues
     * @param msg Un message decrivant l'erreur
     */
    public NonRelatifException(Point2D p, String msg) {
	super(msg);
	point = p;
    }

    /**
     * Obtient le point en coordonnees absolues
     * 
     * @return Le point en coordonnees absolues
     */
    public Point2D getPoint() {
	return point;
    }
}
