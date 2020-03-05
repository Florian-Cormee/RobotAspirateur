package fr.rob4.simulation.vue.forme;

import fr.rob4.simulation.geometrie.*;
import fr.rob4.simulation.vue.IDessinateur;

public final class GeometrieDessinateurFactory {
    public final static GeometrieDessinateurFactory instance = new GeometrieDessinateurFactory();

    private GeometrieDessinateurFactory() {
    }

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
     * Crée une nouvelle instance de dessinateur d'arc de cercle
     *
     * @return Un dessinateur d'arc de cercle
     */
    public IDessinateur<ArcDeCercle> arcDeCercle() {
	return new ArcDeCercleDessinateur();
    }

    /**
     * Crée une nouvelle instance de dessinateur de rectangle
     *
     * @return Un dessinateur de rectangle
     */
    public IDessinateur<Rectangle> rectangle() {
	return new RectangleDessinateur();
    }

    /**
     * Crée une nouvelle instance de dessinateur de polygone
     *
     * @return Un dessinateur de poylgone
     */
    public IDessinateur<Polygone> polygone() {
	return new PolygonDessinateur();
    }

    /**
     * Crée une nouvelle instance de dessinateur de segment
     * 
     * @return Un dessinateur de segment
     */
    public IDessinateur<Segment> segment() {
	return new SegmentDessinateur();
    }
}
