package fr.rob4.simulation.element;

import fr.rob4.simulation.IActuallisable;
import fr.rob4.simulation.geometrie.Forme;

public interface IElement extends IActuallisable {
    Forme getForme();
}
