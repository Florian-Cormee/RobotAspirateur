package fr.rob4.simulation;

import fr.rob4.simulation.element.*;
import fr.rob4.simulation.element.module.CapteurSalete;

import java.util.*;

public class Simulation {
    /** Période d'échantillonage (en secondes) */
    public static final double T = 1e-2;

    protected List<IElement> elements;
    protected ICollisionable bordures;

    /**
     * Crée une Simulation vide
     *
     * @param bordures Bordures de la zone de simulation
     */
    public Simulation(ICollisionable bordures) {
        this.bordures = Objects.requireNonNull(bordures);
        this.elements = new ArrayList<>();
        this.elements.add(bordures);
    }

    /**
     * Crée une Simulation avec les éléments donnés
     *
     * @param bordures Bordures de la zone de simulation
     * @param elements Les éléments à inclure dans la simulation
     */
    public Simulation(ICollisionable bordures, Collection<? extends IElement> elements) {
        this.bordures = Objects.requireNonNull(bordures);
        this.elements = new ArrayList<>(elements);
        this.elements.add(bordures);
    }

    /**
     * Actualise la simulation
     * <p>
     * L'actualisation est réalisée dans cet ordre:
     *     <ol>
     *         <li>Gestion des collisions</li>
     *         <li>Actualisation des éléments</li>
     *         <li>Nettoyage des tâches par les robots</li>
     *     </ol>
     * </p>
     */
    protected void actualise() {
        /* Gestion des collisions */
        List<ICollisionable> collisionables = getElements(ICollisionable.class);
        // Parcourt tout les couples de collisionables
        for (int i = 0 ; i < collisionables.size() ; i++) {
            ICollisionable collI = collisionables.get(i);
            for (int j = i ; j < collisionables.size() ; j++) {
                ICollisionable collJ = collisionables.get(j);
                // Test de la collision
                if (collI.collisionne(collJ)) {
                    // Chaque collisionable gère la collision avec l'autre
                    collI.gereCollision(collJ);
                    collJ.gereCollision(collI);
                }
            }
        }
        /* Actualisation des éléments */
        List<IActuallisable> actuallisables = getElements(IActuallisable.class);
        for (IActuallisable actuallisable : actuallisables) {
            actuallisable.actualise(this);
        }
        /* Nettoyage */
        List<IRobot> robots = getElements(IRobot.class);
        for (IRobot robot : robots) {
            // Test que le robot nettoie
            if (robot.isNettoie()) {
                // Supprime toutes les tâches vues par le(s) capteur(s) de saletés
                List<CapteurSalete> capteurSaletes = robot.getModules(CapteurSalete.class);
                for (CapteurSalete capteur : capteurSaletes) {
                    Set<INettoyable> nettoyables = capteur.getNettoyables();
                    elements.removeAll(nettoyables);
                }
            }
        }
    }

    /**
     * Obtient tous les éléments d'un type particulier
     *
     * @param c   La classe du type demandé
     * @param <T> Le type demandé
     *
     * @return Une liste contenant tous les éléments du type demandé (vide si il n'y en a aucun)
     */
    public <T extends IElement> List<T> getElements(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (IElement element : elements) {
            if (c.isInstance(element)) {
                list.add(c.cast(element));
            }
        }
        return list;
    }

    /**
     * Obtient les bordures
     * @return Les bordures
     */
    public ICollisionable getBordures() { return bordures;}

    /**
     * Obtient une vue non modifiable de la liste des éléments
     *
     * @return Une vue non modifiable de la liste des éléments
     */
    public List<IElement> getElements() {
        return Collections.unmodifiableList(elements);
    }
}
