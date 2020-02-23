package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;

public interface IActuallisable extends IElement {
    /**
     * Actualise l'éléments
     * <p>
     * L'actualisation a toujours lieu après la gestion des collisions.
     * </p>
     *
     * @param simulation La simulation qui demandant l'actualisation
     */
    void actualise(Simulation simulation);
}
