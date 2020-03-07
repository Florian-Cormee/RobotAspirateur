package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.Tache;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

public class NettoyableDessinable extends Tache implements IDessinable {
    private Color couleur;

    public NettoyableDessinable(Forme forme, Color couleur) {
        super(forme);
        this.couleur = Objects.requireNonNull(couleur);
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precCouleur = graphics2D.getColor();
        graphics2D.setColor(this.couleur);

        IDessinateur<Forme> dessinateur = GeometrieDessinateurFactory.instance.forme();
        dessinateur.dessine(graphics2D, echelle, true, this.forme);

        graphics2D.setColor(precCouleur);
    }
}
