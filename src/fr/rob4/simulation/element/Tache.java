package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;

public class Tache extends Element implements INettoyable {

    /**
     * Crée une tache à partir de sa forme.
     *
     * @param forme La forme (ne peut être <code>null</code>)
     */
    public Tache(Forme forme) {
        super(forme);
    }

}
