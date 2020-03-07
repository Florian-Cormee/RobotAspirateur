package fr.rob4.simulation;

import fr.rob4.simulation.element.IActuallisable;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IElement;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.CapteurSalete;
import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Simulation {
    /** Période d'échantillonage (en secondes) */
    public static final double T = 1e-2;

    protected List<IElement> elements;
    protected ICollisionable bordures;

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
        /* Actualisation des éléments */
        List<IActuallisable> actuallisables = this.getElements(IActuallisable.class);
        for (IActuallisable actuallisable : actuallisables) {
            actuallisable.actualise(this);
        }
        /* Gestion des collisions */
        List<ICollisionable> collisionables = this.getElements(ICollisionable.class);
        // Parcourt tout les couples de collisionables
        for (int i = 0 ; i < collisionables.size() ; i++) {
            ICollisionable collI = collisionables.get(i);
            for (int j = i ; j < collisionables.size() ; j++) {
                if (i == j) {
                    continue;
                }
                ICollisionable collJ = collisionables.get(j);
                // Test de la collision
                try {
                    if (collI.collisionne(collJ)) {
                        // Chaque collisionable gère la collision avec l'autre
                        collI.gereCollision(collJ);
                        collJ.gereCollision(collI);
                    }
                } catch (NoIntersectionException e) {
                    // On ne sait pas détecter les collision entre ces éléments
                    e.printStackTrace();
                }
            }
        }
        /* Nettoyage */
        List<IRobot> robots = this.getElements(IRobot.class);
        for (IRobot robot : robots) {
            // Test que le robot nettoie
            if (robot.isNettoie()) {
                // Supprime toutes les tâches vues par le(s) capteur(s) de saletés
                List<CapteurSalete> capteurSaletes = robot.getModules(CapteurSalete.class);
                for (CapteurSalete capteur : capteurSaletes) {
                    Set<INettoyable> nettoyables = capteur.getNettoyables();
                    this.elements.removeAll(nettoyables);
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
    public <T extends IElement> List<T> getElements(Class<? extends T> c) {
        List<T> list = new ArrayList<>();
        for (IElement element : this.elements) {
            if (c.isInstance(element)) {
                T elementCast = c.cast(element);
                list.add(elementCast);
            }
        }
        return list;
    }

    /**
     * Obtient les bordures
     *
     * @return Les bordures
     */
    public ICollisionable getBordures() { return this.bordures;}

    /**
     * Obtient une vue non modifiable de la liste des éléments
     *
     * @return Une vue non modifiable de la liste des éléments
     */
    public List<IElement> getElements() {
        return Collections.unmodifiableList(this.elements);
    }
}
