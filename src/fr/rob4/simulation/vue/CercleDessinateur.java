package fr.rob4.simulation.vue;

import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;

import java.awt.Graphics2D;

public class CercleDessinateur implements IDessinateur<Cercle> {

    public CercleDessinateur() {
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle, Cercle c) {
        Point2D centre = c.getCentre();
        Vecteur2D pos = centre.getPositionAbsolue();
        double longueurD = c.getDiametre() * echelle;
        int x = (int) (pos.getX() * echelle - (longueurD / 2));
        int y = (int) (pos.getY() * echelle - (longueurD / 2));
        int longueur = (int) longueurD;
        graphics2D.drawOval(x, y, longueur, longueur);
    }

    @Override
    public Class<Cercle> getType() {
        return Cercle.class;
    }
}
