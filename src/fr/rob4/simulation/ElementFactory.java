package fr.rob4.simulation;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.strategie.IStrategie;
import fr.rob4.simulation.vue.ElementDessinableFactory;

public abstract class ElementFactory {
    private static ElementFactory instance;

    /**
     * Obtient l'instance de la factory
     * <p>
     * Si aucune instance n'existe déjà, une nouvelle est crée.
     *
     * @return Une factory d'éléments
     */
    public static ElementFactory getInstance() {
        if (instance == null) {
            synchronized (ElementFactory.class) {
                if (instance == null) {
                    instance = new ElementDessinableFactory();
                }
            }
        }
        return instance;
    }

    /**
     * Crée un capteur de contact à partir de sa zone de déclenchement
     *
     * @param forme La zone de déclenchement
     *
     * @return Un capteur de contact à partir de sa zone de déclenchement
     */
    public abstract IModule<Boolean> capteurContact(Forme forme);

    /**
     * Crée un capteur de saleté à partir de sa zone de déclenchement
     *
     * @param forme La zone de déclenchement
     *
     * @return Un capteur de saleté à partir de sa zone de déclenchement
     */
    public abstract IModule<Boolean> capteurSalete(Forme forme);

    /**
     * Construit un obstacle à partir de sa forme.
     *
     * @param forme La forme (ne peut être <code>null</code>)
     *
     * @return Un obstacle collisionable.
     */
    public abstract ICollisionable obstacle(Forme forme);

    /**
     * Construit un robot
     *
     * @param forme       La forme (ne peut être <code>null</code>)
     * @param strategie   La strategie à adopter
     * @param orientation L'orientation initiale (en radians)
     * @param ecartRoues  La distance séparant les deux roues
     * @param modules     Les modules embarqués
     *
     * @return Un robot
     */
    public abstract IRobot robot(Forme forme,
                                 IStrategie strategie,
                                 double orientation,
                                 double ecartRoues,
                                 IModule<?>... modules);

    /**
     * Crée une tache à partir de sa forme.
     *
     * @param forme La forme (ne peut être <code>null</code>)
     *
     * @return Une tache.
     */
    public abstract INettoyable tache(Forme forme);
}
