package fr.rob4.simulation.vue.element.module;

import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.LocalTime;

public class CapteurContactDessinable extends CapteurContact implements IDessinable {
    private LocalTime changementCouleur;
    private boolean dernierEtat;

    public CapteurContactDessinable(Forme forme) {
        super(forme);
        this.changementCouleur = LocalTime.now();
        this.dernierEtat = false;
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color c = graphics2D.getColor();
        // Dessine le capteur avec une couleur différente selon sont état
        Duration d = Duration.between(this.changementCouleur, LocalTime.now());
        if (d.compareTo(CapteurDessinable.DUREE_CHANGEMENT) > 0) {
            graphics2D.setColor(this.getInfo() ? CapteurDessinable.COULEUR_ACTIVE :
                                        CapteurDessinable.COULEUR_DESACTIVE);
            if (this.dernierEtat != this.getInfo()) {
                this.changementCouleur = LocalTime.now();
                this.dernierEtat = this.getInfo();
            }
        }
        IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
        dessForme.dessine(graphics2D, echelle, false, this.forme);

        graphics2D.setColor(c);
    }

    @Override
    public String toString() {
        return "CapteurContactDessinable{} " + super.toString();
    }
}
