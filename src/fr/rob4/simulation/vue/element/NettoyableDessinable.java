package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;

public class NettoyableDessinable implements INettoyable, IDessinable {
    private INettoyable collisionable;
    private Color couleur;

    public NettoyableDessinable(INettoyable nettoyable, Color couleur) {
        this.collisionable = nettoyable;
        this.couleur = couleur;
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precCouleur = graphics2D.getColor();
        graphics2D.setColor(this.couleur);

        Forme forme = this.collisionable.getForme();
        IDessinateur<Forme> dessinateur = GeometrieDessinateurFactory.instance.forme();
        dessinateur.dessine(graphics2D, echelle, true, forme);

        graphics2D.setColor(precCouleur);
    }

    @Override
    public Forme getForme() {
        return this.collisionable.getForme();
    }
}
