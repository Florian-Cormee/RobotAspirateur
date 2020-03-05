package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class ObstacleDessinable implements ICollisionable, IDessinable {
    private ICollisionable collisionable;
    private Color couleur;

    public ObstacleDessinable(ICollisionable collisionable, Color couleur) {
        this.collisionable = collisionable;
        this.couleur = couleur;
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precCouleur = graphics2D.getColor();
        Stroke precStroke = graphics2D.getStroke();
        graphics2D.setColor(this.couleur);
        graphics2D.setStroke(new BasicStroke(5));
        
        Forme forme = this.collisionable.getForme();
        IDessinateur<Forme> dessinateur = GeometrieDessinateurFactory.instance.forme();
        dessinateur.dessine(graphics2D, echelle, false, forme);
        
        graphics2D.setColor(precCouleur);
        graphics2D.setStroke(precStroke);
    }

    @Override
    public void gereCollision(ICollisionable element) {
        this.collisionable.gereCollision(element);
    }

    @Override
    public Forme getForme() {
        return this.collisionable.getForme();
    }
}
