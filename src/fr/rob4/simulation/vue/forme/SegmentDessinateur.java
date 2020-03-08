package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Segment;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;

public class SegmentDessinateur implements IDessinateur<Segment> {
    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, Segment element) {
        // Convertit les mètres en pixels
        Point2D a = element.getA();
        int[] pA = this.versPositionEnPixels(a, echelle);
        Point2D b = element.getB();
        int[] pB = this.versPositionEnPixels(b, echelle);
        // Dessine le segment
        graphics2D.drawLine(pA[0], pA[1], pB[0], pB[1]);
    }

    /**
     * Convertit les coordonnées du points en pixels
     *
     * @param p       Le point dont on veut convertir les coordonnées
     * @param echelle L'échelle de conversion en mètres/pixels
     *
     * @return Un tableau contenant la coordonnée en X puis en Y du point en pixels
     */
    private int[] versPositionEnPixels(Point2D p, double echelle) {
        Vecteur2D pos = p.getPositionAbsolue();
        int[] pixelPos = new int[2];
        pixelPos[0] = (int) (pos.getX() * echelle);
        pixelPos[1] = (int) (pos.getY() * echelle);
        return pixelPos;
    }
}
