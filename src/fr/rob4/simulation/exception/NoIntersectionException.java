/**
 * 
 */
package fr.rob4.simulation.exception;

import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Segment;

/**
 * Cette classe permet de soulever une exception lorsque qu'aucune intersection
 * entre des formes est trouvé.
 * 
 * @author Florentin BEROUJON & Forian CORMEE
 * @version 0.0.1
 * @see Forme
 * @see Segment
 *
 */
@SuppressWarnings("serial")
public class NoIntersectionException extends Exception {
	private Forme forme;

	/**
	 * Créer une exception à partir de la forme responsable
	 * 
	 * @param f Forme respponsable.
	 */
	public NoIntersectionException(Forme f) {
		super();
		forme = f;
	}

	/**
	 * Créer une exception à partir de la forme responsable et d'un message.
	 * 
	 * @param f   Forme responsable.
	 * @param msg Message.
	 */
	public NoIntersectionException(Forme f, String msg) {
		super(msg);
		forme = f;
	}

	/**
	 * Renvoie la forme responsable
	 * 
	 * @return La forme
	 */
	public Forme getForme() {
		return forme;
	}
}
