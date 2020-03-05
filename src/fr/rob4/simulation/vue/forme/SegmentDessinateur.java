package fr.rob4.simulation.vue.forme;

import java.awt.Graphics2D;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Segment;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

public class SegmentDessinateur implements IDessinateur<Segment> {
    public SegmentDessinateur() {
    }

    private int[] versPositionEnPixels(Point2D p, double echelle) {
	Vecteur2D pos = p.getPositionAbsolue();
	int pixelPos[] = new int[2];
	pixelPos[0] = (int) (pos.getX() * echelle);
	pixelPos[1] = (int) (pos.getY() * echelle);
	return pixelPos;
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli,
	    Segment element) {
	// Converti les mètres en pixels
	Point2D a = element.getA();
	int pA[] = versPositionEnPixels(a, echelle);
	Point2D b = element.getB();
	int pB[] = versPositionEnPixels(b, echelle);
	// Dessine le segment
	graphics2D.drawLine(pA[0], pA[1], pB[0], pB[1]);
    }
}
