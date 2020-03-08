package fr.rob4.simulation.strategie;

import fr.rob4.simulation.element.IRobot;

public interface IStrategie {
    /**
     * Prends une décision selon le context du robot
     * <p>
     * Note: La stratégie repose souvent sur les états précédant du robot ; l'instance ne devrait gérer qu'un robot,
     * sauf implémentation particulière.
     *
     * @param robot Le robot qui nécessite une décision
     */
    void decide(IRobot robot);
}
