/**
 * 
 */
package fr.rob4.simulation;

/**
 * Classe permettant de faire appelle à certaines méthodes mathématiques utiles.
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 *
 */
public final class Outil {

	private Outil() {};
	
	public static double normalize_angle(double a) {
		while (a > Math.PI)
		    a -= 2 * Math.PI;
		while (a < -Math.PI)
		    a += 2 * Math.PI;
		return a;
	    }
}
