package fr.rob4.simulation.vue.forme;

import com.sun.xml.internal.bind.v2.model.core.ID;
import fr.rob4.simulation.geometrie.*;
import fr.rob4.simulation.vue.IDessinateur;

import java.awt.Graphics2D;

public class FormeDessinateur implements IDessinateur<Forme> {
    @Override
    public void dessine(Graphics2D graphics2D, double echelle, boolean rempli, Forme element) {
        if (element.getClass() == Cercle.class) {
            IDessinateur<Cercle> dessinateur = GeometrieDessinateurFactory.instance.cercle();
            dessinateur.dessine(graphics2D, echelle, rempli, (Cercle) element);
        } else if (element.getClass() == Rectangle.class) {
            IDessinateur<Rectangle> dessinateur = GeometrieDessinateurFactory.instance.rectangle();
            dessinateur.dessine(graphics2D, echelle, rempli, (Rectangle) element);
        } else if (element.getClass() == ArcDeCercle.class) {
            IDessinateur<ArcDeCercle> dessinateur = GeometrieDessinateurFactory.instance.arcDeCercle();
            dessinateur.dessine(graphics2D, echelle, rempli, (ArcDeCercle) element);
        } else if (element.getClass() == Polygone.class) {
            IDessinateur<Polygone> dessinateur = GeometrieDessinateurFactory.instance.polygone();
            dessinateur.dessine(graphics2D, echelle, rempli, (Polygone) element);
        } else {
            String msg = String.format("Ne peut pas dessiner '%s'\n", element);
            throw new UnsupportedOperationException(msg);
        }
    }
}
