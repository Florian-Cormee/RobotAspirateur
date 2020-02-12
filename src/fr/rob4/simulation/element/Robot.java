package fr.rob4.simulation.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.rob4.simulation.Position;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.strategie.IStrategie;

public class Robot extends Element implements ICollisionable {
    protected double vitesse;
    protected List<IModule<?>> modules;
    protected IStrategie strategie;

    public Robot(Forme forme, Collection<? extends IModule<?>> capteurs,
	    IStrategie strategie, double vitesse) {
	super(forme);
	this.vitesse = vitesse;
	this.strategie = Objects.requireNonNull(strategie);

	this.modules = new ArrayList<>(capteurs);
    }

    public Position getPosition() {
	return forme.getCentre();
    }

    public Position deplace(double d_l, double d_r, double ecartRoues) {
	return getPosition().move(d_l, d_r, ecartRoues);
    }

    public double getVitesse() {
	return vitesse;
    }

    public void setVitesse(double vitesse) {
	this.vitesse = vitesse;
    }

    public IStrategie getStrategie() {
	return strategie;
    }

    public List<IModule<?>> getModules() {
	return Collections.unmodifiableList(modules);
    }

    public <T extends IModule<?>> List<T> getModules(Class<T> c) {
	List<T> list = new ArrayList<>();
	for (IModule<?> module : modules) {
	    if (c.isInstance(module)) {
		list.add(c.cast(module));
	    }
	}
	return list;
    }

    @Override
    public boolean gereCollision(Element element) {
	// TODO Auto-generated method stub
	return false;
    }
    
    @Override
    public boolean actualise(Simulation simulation) {
	for (IModule<?> module : modules) {
	    module.actualise(simulation);
	}
	strategie.decide(this);
	return true;
    }
}
