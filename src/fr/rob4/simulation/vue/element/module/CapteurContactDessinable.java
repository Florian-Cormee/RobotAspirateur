package fr.rob4.simulation.vue.element.module;

import fr.rob4.simulation.element.module.CapteurContact;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;

public class CapteurContactDessinable extends CapteurContact implements IDessinable {
    public CapteurContactDessinable(Forme forme) {
        super(forme);
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color c = graphics2D.getColor();

        graphics2D.setColor(new Color(0, 50, 255, 100));
        IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
        dessForme.dessine(graphics2D, echelle, false, this.forme);

        graphics2D.setColor(c);
    }

    @Override
    public String toString() {
        return "CapteurContactDessinable{} " + super.toString();
    }
}
