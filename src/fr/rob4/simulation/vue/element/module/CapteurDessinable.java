package fr.rob4.simulation.vue.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.Color;
import java.awt.Graphics2D;

public class CapteurDessinable<T> implements IModule<T>, IDessinable {
    private IModule<T> capteur;
    private boolean plein;

    public CapteurDessinable(IModule<T> capteur, boolean plein) {
        super();
        this.capteur = capteur;
        this.plein = plein;
    }

    @Override
    public void actualise(Simulation simulation) {
        this.capteur.actualise(simulation);
    }

    @Override
    public void dessine(Graphics2D graphics2d, double echelle) {
        Color c = graphics2d.getColor();

        graphics2d.setColor(new Color(0, 50, 255, 100));
        Forme forme = this.capteur.getForme();
        IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
        dessForme.dessine(graphics2d, echelle, this.plein, forme);

        graphics2d.setColor(c);
    }

    @Override
    public Forme getForme() {
        return this.capteur.getForme();
    }

    @Override
    public T getInfo() {
        return this.capteur.getInfo();
    }

    @Override
    public void rotation(double angle, Point2D centre) {
        this.capteur.rotation(angle, centre);
    }

    @Override
    public void translation(Vecteur2D deplacement) {
        this.capteur.translation(deplacement);
    }
}
