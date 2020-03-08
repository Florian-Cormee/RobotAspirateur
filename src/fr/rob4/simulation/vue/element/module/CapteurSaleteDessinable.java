package fr.rob4.simulation.vue.element.module;

import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;

public class CapteurSaleteDessinable extends CapteurSalete implements IDessinable {
    /**
     * Crée un CapteurSalete à partir de sa zone de détection
     *
     * @param forme La zone de détection
     */
    public CapteurSaleteDessinable(Forme forme) {
        super(forme);
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color c = graphics2D.getColor();
        // Dessine le capteur avec une couleur différente selon sont état
        graphics2D.setColor(this.getInfo() ? CapteurDessinable.COULEUR_ACTIVE : CapteurDessinable.COULEUR_DESACTIVE);
        IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
        dessForme.dessine(graphics2D, echelle, true, this.forme);

        graphics2D.setColor(c);
    }

    @Override
    public String toString() {
        return "CapteurSaleteDessinable[super.toString()=" + super.toString() + ']';
    }
}
