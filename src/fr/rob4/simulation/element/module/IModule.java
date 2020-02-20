package fr.rob4.simulation.element.module;

import fr.rob4.simulation.element.IActuallisable;

public interface IModule<T> extends IActuallisable {
    T getInfo();
}
