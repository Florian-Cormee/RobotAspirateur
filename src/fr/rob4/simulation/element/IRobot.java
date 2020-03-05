package fr.rob4.simulation.element;

import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.strategie.IStrategie;

import java.util.List;

public interface IRobot extends ICollisionable, IActuallisable {
    /**
     * D�place en fonction des distances parcourues par les roues
     *
     * @param dG La distance parcourue par la roue gauche
     * @param dD La distance parcourue par la roue droite
     */
    void deplace(double dG, double dD);

    /**
     * Obtient une liste non modifiable de tous les modules
     *
     * @return Une liste non modifiable de tous les modules
     */
    public List<IModule<?>> getModules();

    /**
     * Obtient une liste des modules du type demand�
     *
     * @param <T> Le type de module
     * @param c   La class du type de module
     *
     * @return Une liste des modules du type demand� (vide s'il n'y en a pas)
     */
    public <T extends IModule<?>> List<T> getModules(Class<T> c);

    /**
     * Obtient la strategie suivie
     *
     * @return La strategie suivie
     */
    public IStrategie getStrategie();

    /**
     * Obtient l'�tat du module de nettoyage
     *
     * @return {@code true} pour que le nettoyage ait lieu sinon {@code false}
     */
    public boolean isNettoie();

    /**
     * Defini si le nettoyage a lieu
     *
     * @param etat {@code true} pour que le nettoyage ait lieu sinon {@code false}
     */
    public void setNettoie(boolean etat);
    

    /**
     * Obtient la position
     *
     * @return La position
     */
    public Point2D getPosition();
    
    /**
     * Obtient l'orientation
     * 
     * @return L'orientation (en radians)
     */
    public double getOrientation();
}
