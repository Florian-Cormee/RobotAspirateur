package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Polygone;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;
import java.util.List;

public class PolygonDessinateur implements IDessinateur<Polygone> {
    public PolygonDessinateur() {
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, Polygone element) {
        List<Point2D> points = element.getPoints();
        int npoints = points.size();
        int[] xs = new int[npoints];
        int[] ys = new int[npoints];
        // Calcule la position de chaque point en pixel
        for (int i = 0 ; i < npoints ; i++) {
            Vecteur2D positionAbsolue = points.get(i).getPositionAbsolue();
            xs[i] = (int) (positionAbsolue.getX() * echelle);
            ys[i] = (int) (positionAbsolue.getY() * echelle);
        }
        // Dessine la figure
        if (rempli) {
            graphics2D.fillPolygon(xs, ys, npoints);
        } else {
            graphics2D.drawPolygon(xs, ys, npoints);
        }
    }
}
