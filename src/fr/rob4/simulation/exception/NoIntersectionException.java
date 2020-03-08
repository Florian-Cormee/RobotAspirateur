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
        this.forme = f;
    }

    /**
     * Créer une exception à partir de la forme responsable et d'un message.
     *
     * @param f   Forme responsable.
     * @param msg Message.
     */
    public NoIntersectionException(Forme f, String msg) {
        super(msg);
        this.forme = f;
    }

    public NoIntersectionException(String message, Throwable cause, Forme forme) {
        super(message, cause);
        this.forme = forme;
    }

    /**
     * Renvoie la forme responsable
     *
     * @return La forme
     */
    public Forme getForme() {
        return this.forme;
    }
}
