package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.vue.IDessinateur;

public final class GeometrieDessinateurFactory {
    public final static GeometrieDessinateurFactory instance = new GeometrieDessinateurFactory();

    private GeometrieDessinateurFactory() {}

    public IDessinateur<Forme> forme() {
        return new FormeDessinateur();
    }

    /**
     * Crée une nouvelle instance de dessinateur de cercle
     *
     * @return Un dessinateur de cercle
     */
    public IDessinateur<Cercle> cercle() {
        return new CercleDessinateur();
    }

    /**
     * Crée une nouvelle instance de dessinateur de rectangle
     *
     * @return Un dessinateur de rectangle
     */
    public IDessinateur<Rectangle> rectangle() {
        return new RectangleDessinateur();
    }
}
