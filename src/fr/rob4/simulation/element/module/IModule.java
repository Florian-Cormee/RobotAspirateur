package fr.rob4.simulation.element.module;

import fr.rob4.simulation.element.IActuallisable;
import fr.rob4.simulation.element.IDeplacable;

public interface IModule<T> extends IActuallisable, IDeplacable {
    T getInfo();
}
