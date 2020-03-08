package fr.rob4.simulation.element.module;

import fr.rob4.simulation.element.IActuallisable;
import fr.rob4.simulation.element.IDeplacable;

public interface IModule<T> extends IActuallisable, IDeplacable {
    /**
     * Obtient l'information mesurée par le module
     *
     * @return L'information mesurée par le module
     */
    T getInfo();
}
