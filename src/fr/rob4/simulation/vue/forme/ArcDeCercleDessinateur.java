package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;

public class ArcDeCercleDessinateur implements IDessinateur<ArcDeCercle> {
    public ArcDeCercleDessinateur() {
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, ArcDeCercle element) {
        Point2D centre = element.getCentre();
        Vecteur2D positionAbsolue = centre.getPositionAbsolue();
        double rayon = element.getRayon() * echelle;
        // Calcule la position du point inférieur gauche et la longueur du côté
        int x = (int) (positionAbsolue.getX() * echelle - rayon);
        int y = (int) (positionAbsolue.getY() * echelle - rayon);
        int longueur = (int) (rayon * 2);
        // Calcule les angles de début et d'ouverture
        int angleDebut = (int) ((element.getOrientation() - element.getOuverture() / 2) * 180 / Math.PI);
        int angleOuverture = (int) (element.getOuverture() * 180 / Math.PI);
        // Trace l'arc de cercle
        if (rempli) {
            graphics2D.fillArc(x, y, longueur, longueur, -angleDebut, -angleOuverture);
        } else {
            graphics2D.drawArc(x, y, longueur, longueur, -angleDebut, -angleOuverture);
        }

    }
}
