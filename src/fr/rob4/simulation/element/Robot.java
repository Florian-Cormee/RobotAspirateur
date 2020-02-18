package fr.rob4.simulation.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;

public class Robot extends Element implements ICollisionable {
    /** Vitesse maximale des robots (en m/s) */
    public static final double VITESSE_MAX = 1;

    protected List<IModule<?>> modules;
    protected IStrategie strategie;
    protected Vecteur2D direction;
    protected double ecartRoues;

    /**
     * Crée un robot
     * 
     * S'assure que les arguments, hormis la collection de capteurs, ne sont pas
     * {@code null} et normalise le vecteur direction donné.
     * <p>
     * Une collection de capteur {@code null} crée une liste de capteurs vide
     * 
     * @param forme      La forme
     * @param capteurs   Les capteurs embarqués
     * @param strategie  La strategie que adopté
     * @param direction  La direction initiale (peut être un vecteur non normé)
     * @param ecartRoues La distance en mètres séparant les roues
     * @see Collections#emptyList()
     */
    public Robot(Forme forme, Collection<? extends IModule<?>> capteurs,
	    IStrategie strategie, Vecteur2D direction, double ecartRoues) {
	super(forme);
	this.strategie = Objects.requireNonNull(strategie);
	this.direction = Objects.requireNonNull(direction).normalise();
	if (capteurs == null) {
	    this.modules = Collections.emptyList();
	} else {
	    this.modules = new ArrayList<>(capteurs);
	}
	this.ecartRoues = ecartRoues;
    }

    /**
     * Obtient la postion
     * 
     * @return La position
     */
    public Point2D getPosition() {
	return forme.getCentre();
    }

    /**
     * Déplace en fonction des distances parcourues par les roues
     * 
     * @param dG La distance parcourue par la roue gauche
     * @param dD La distance parcourue par la roue droite
     */
    public void deplace(double dG, double dD) {
	// Mise à jour de la position & limitation par la vitesse du déplacement
	double distance = Math.min((dG + dD) / 2, VITESSE_MAX * Simulation.T);
	Vecteur2D deplacement = direction.produit(distance);
	Point2D pos = getPosition().deplace(deplacement);
	forme.setCentre(pos);
	// Mise à jour de la direction
	Vecteur2D uCA = direction.rotation(Math.PI / 2);
	Vecteur2D dir = direction.addition(uCA.produit((dD - dG) / ecartRoues))
		.normalise();
	double dtheta = direction.getAngle(dir);
	forme = forme.rotation(dtheta);
    }

    /**
     * Obtient la strategie suivie
     * 
     * @return La strategie suivie
     */
    public IStrategie getStrategie() {
	return strategie;
    }

    /**
     * Obtient une liste non modifiable de tous les modules
     * 
     * @return Une liste non modifiable de tous les modules
     */
    public List<IModule<?>> getModules() {
	return Collections.unmodifiableList(modules);
    }

    /**
     * Obtient une liste des modules du type demandé
     * 
     * @param <T> Le type de module
     * @param c   La class du type de module
     * @return Une liste des modules du type demandé (vide s'il n'y en a pas)
     */
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

    /**
     * Actualise les modules puis la strategie
     */
    @Override
    public boolean actualise(Simulation simulation) {
	for (IModule<?> module : modules) {
	    module.actualise(simulation);
	}
	strategie.decide(this);
	return true;
    }

    @Override
    public String toString() {
	return "Robot [modules=" + modules + ", strategie=" + strategie
		+ ", direction=" + direction + ", toString()="
		+ super.toString() + "]";
    }
}
