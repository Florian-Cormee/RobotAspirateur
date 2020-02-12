/**
 * 
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.Position;

/**
 * @author flore
 *
 */
public class Cercle extends Forme {

	// Attribut
	protected double diametre;
	/**
	 * @param p
	 */
	public Cercle(Position p , double d) {
		super(p);
		diametre = d;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Cercle(double x, double y, double d) {
		super(x, y);
		diametre = d;
	}

	/* (non-Javadoc)
	 * @see fr.rob4.simulation.geometrie.Forme#rotation(double, fr.rob4.simulation.Position)
	 */
	@Override
	public Forme rotation(double alpha, Position p) {
		// TODO Auto-generated method stub
		return null;
	}

}
