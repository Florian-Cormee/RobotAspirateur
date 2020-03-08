package fr.rob4.simulation.vue.element.module;

import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.time.Duration;
import java.time.LocalTime;

public class CapteurContactDessinable extends CapteurContact implements IDessinable {
    private LocalTime changementCouleur;
    private Color derniereCouleur;

    public CapteurContactDessinable(Forme forme) {
        super(forme);
        this.changementCouleur = LocalTime.now();
        this.derniereCouleur = CapteurDessinable.COULEUR_DESACTIVE;
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        // Sauvegarde de la configuration du Graphics2D
        Color c = graphics2D.getColor();
        Stroke stroke = graphics2D.getStroke();
        // Dessine le capteur avec une couleur différente selon sont état
        Duration d = Duration.between(this.changementCouleur, LocalTime.now());
        if (d.compareTo(CapteurDessinable.DUREE_CHANGEMENT) > 0) { // Le dernier changement de couleur est périmé
            // On choisi la nouvelle couleur
            Color couleur = this.getInfo() ? CapteurDessinable.COULEUR_ACTIVE : CapteurDessinable.COULEUR_DESACTIVE;
            if (this.derniereCouleur != couleur) {
                // Si la couleur a changée, on reset le chronomètre et on sauvegarde la nouvelle couleur
                this.changementCouleur = LocalTime.now();
                this.derniereCouleur = couleur;
            }
        }
        graphics2D.setColor(this.derniereCouleur);
        graphics2D.setStroke(new BasicStroke(2));
        IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
        dessForme.dessine(graphics2D, echelle, false, this.forme);
        // Reinitialisation du Graphics2D
        graphics2D.setColor(c);
        graphics2D.setStroke(stroke);
    }

    @Override
    public String toString() {
        return "CapteurContactDessinable[super.toString()=" + super.toString() + ']';
    }
}
