package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;

public class RectangleDessinateur implements IDessinateur<Rectangle> {
    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, Rectangle element) {
        Point2D centre = element.getCentre();
        Vecteur2D position = centre.getPositionAbsolue();
        Rectangle dimension = element.getDimension();
        int x = (int) ((position.getX() - dimension.getLargeur() / 2) * echelle);
        int y = (int) ((position.getY() - dimension.getHauteur() / 2) * echelle);
        int l = (int) (dimension.getLargeur() * echelle);
        int h = (int) (dimension.getHauteur() * echelle);
        // Dessine la figure
        if (rempli) {
            graphics2D.fillRect(x, y, l, h);
        } else {
            graphics2D.drawRect(x, y, l, h);
        }
    }
}
