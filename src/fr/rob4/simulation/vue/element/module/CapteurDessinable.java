package fr.rob4.simulation.vue.element.module;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

public class CapteurDessinable<T>
	implements IModule<T>, IDessinable {
    private IModule<T> capteur;
    private boolean plein;

    public CapteurDessinable(IModule<T> capteur, boolean plein) {
	super();
	this.capteur = capteur;
	this.plein = plein;
    }

    @Override
    public void actualise(Simulation simulation) {
	capteur.actualise(simulation);
    }

    @Override
    public Forme getForme() {
	return capteur.getForme();
    }

    @Override
    public void dessine(Graphics2D graphics2d, double echelle) {
	Color c = graphics2d.getColor();
	
	graphics2d.setColor(new Color(0, 50, 255, 100));
	Forme forme = capteur.getForme();
	IDessinateur<Forme> dessForme = GeometrieDessinateurFactory.instance.forme();
	dessForme.dessine(graphics2d, echelle, plein, forme);
	
	graphics2d.setColor(c);
    }

    @Override
    public T getInfo() {
	return capteur.getInfo();
    }
}
