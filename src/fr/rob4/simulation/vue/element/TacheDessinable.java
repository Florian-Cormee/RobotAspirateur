package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.Tache;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

public class TacheDessinable extends Tache implements IDessinable {
    private Color couleur;

    /**
     * Crée un nettoyable dessinable
     * @param forme La forme de la tache
     * @param couleur La couleur de la tache
     */
    public TacheDessinable(Forme forme, Color couleur) {
        super(forme);
        this.couleur = Objects.requireNonNull(couleur);
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precCouleur = graphics2D.getColor();
        graphics2D.setColor(this.couleur);
        // Appelle le dessinateur pour la forme de la tache
        IDessinateur<Forme> dessinateur = GeometrieDessinateurFactory.instance.forme();
        dessinateur.dessine(graphics2D, echelle, true, this.forme);

        graphics2D.setColor(precCouleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.couleur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TacheDessinable that = (TacheDessinable) o;
        return Objects.equals(this.couleur, that.couleur);
    }

    @Override
    public String toString() {
        return "NettoyableDessinable[" + "couleur=" + this.couleur + ", super.toString()=" + super.toString() + ']';
    }
}
