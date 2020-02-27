package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;

public class CercleDessinateur implements IDessinateur<Cercle> {
    public CercleDessinateur() {}

    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, Cercle c) {
        Point2D centre = c.getCentre();
        Vecteur2D pos = centre.getPositionAbsolue();
        double rayon = c.getRayon() * echelle;
        int x = (int) (pos.getX() * echelle - rayon);
        int y = (int) (pos.getY() * echelle - rayon);
        int longueur = (int) rayon*2;
        if (rempli) {
            graphics2D.fillOval(x, y, longueur, longueur);
        } else {
            graphics2D.drawOval(x, y, longueur, longueur);
        }
    }
}
