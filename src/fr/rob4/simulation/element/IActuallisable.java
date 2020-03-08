package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;

public interface IActuallisable extends IElement {
    /**
     * Actualise l'éléments
     * <p>
     * L'actualisation a toujours lieu avant la gestion des collisions.
     *
     * @param simulation La simulation qui demandant l'actualisation
     */
    default void actualise(Simulation simulation) {
        this.actualise(simulation, null);
    }

    /**
     * Actualise l'éléments
     * <p>
     * L'actualisation a toujours lieu avant la gestion des collisions.
     *
     * @param simulation La simulation qui demandant l'actualisation
     * @param appeleur L'Objet demandant la mise à jour
     */
    void actualise(Simulation simulation, Object appeleur);
}
