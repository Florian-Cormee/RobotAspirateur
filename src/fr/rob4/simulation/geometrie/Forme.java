/**
 * 
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.Position;

/**
 * @author flore
 *
 */
public abstract class Forme {
	
	// Attribut
	protected Position centre;
	
	public Forme( Position p ) {
		centre = p;
	}
	
	public Forme( double x, double y ) {
		centre = new Position(x, y, 0);
	}

	public Position getCentre() {
		return centre;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}
	
	public boolean estSupperposee( Forme f ) {
		throw new UnsupportedOperationException();
	}
	
	public Forme rotation( double alpha ) {
		return rotation(alpha,centre);
	}
	
	public abstract Forme rotation( double alpha, Position p );
}
