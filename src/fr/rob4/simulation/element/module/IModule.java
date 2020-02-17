package fr.rob4.simulation.element.module;

import fr.rob4.simulation.element.IElement;

public interface IModule<T> extends IElement {
    T getInfo();
}
