package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;

import java.util.List;

public class CapteurLaser extends Element implements IModule<Double> {
    protected double distance;

    public CapteurLaser(Forme forme) {
        super(forme);
        distance = Double.POSITIVE_INFINITY;
    }

    @Override
    public void actualise(Simulation simulation) {
        List<ICollisionable> collisionables = simulation.getElements(ICollisionable.class);
        distance = Double.POSITIVE_INFINITY;
        Point2D centre = forme.getCentre();

        for (ICollisionable collisionable : collisionables) {
            try {
		if (!(collisionable instanceof Robot) && forme.collisionne(collisionable.getForme())) {
		    // On cherche les elements dans le rayon de mesure
		    continue;
		}
	    } catch (NoIntersectionException e) {
		e.printStackTrace();
		// On ne sait pas déterminer s'il y a superposition donc on passe au suivant
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
    }

    @Override
    public Double getInfo() {
        return distance;
    }

}
