package fr.rob4.simulation.element.module;

import java.util.List;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;

public class CapteurLaser extends Element implements IModule<Double> {
    protected double distance;

    public CapteurLaser(Forme forme) {
	super(forme);
	distance = Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean actualise(Simulation simulation) {
	List<ICollisionable> collisionables = simulation
		.getElements(ICollisionable.class);
	distance = Double.POSITIVE_INFINITY;
	Point2D centre = forme.getCentre();

	for (ICollisionable collisionable : collisionables) {
	    if (!(collisionable instanceof Robot)
		    && forme.estSupperposee(collisionable.getForme())) {
		// On cherche les elements dans le rayon de mesure
		continue;
	    }
	    // Calcul de la distance séparant le centre de l'obstacle et du
	    // capteur
	    Forme colForme = collisionable.getForme();
	    Point2D colCentre = colForme.getCentre();
	    Vecteur2D colPosRelative = centre.getPositionRelative(colCentre);
	    double d = colPosRelative.norme();
	    if (d < distance) {
		// On retient la distance la plus petite
		distance = d;
	    }
	}
	return true;
    }

    @Override
    public Double getInfo() {
	return distance;
    }

}
