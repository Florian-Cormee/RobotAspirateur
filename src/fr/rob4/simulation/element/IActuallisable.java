package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;

public interface IActuallisable extends IElement {
    /**
     * Actualise l'�l�ments
     * <p>
     * L'actualisation a toujours lieu apr�s la gestion des collisions.
     * </p>
     *
     * @param simulation La simulation qui demandant l'actualisation
     */
    void actualise(Simulation simulation);
}
