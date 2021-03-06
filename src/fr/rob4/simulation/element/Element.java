package fr.rob4.simulation.element;

import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;

import java.util.Objects;

public abstract class Element implements IDeplacable {
    protected Forme forme;

    public Element(Forme forme) {
        this.forme = Objects.requireNonNull(forme);
    }

    public Forme getForme() {
        return this.forme;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.forme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Element element = (Element) o;
        return Objects.equals(this.forme, element.forme);
    }

    @Override
    public String toString() {
        return "Element[" + "forme=" + this.forme + ']';
    }

    @Override
    public void rotation(double angle, Point2D centre) {
        this.forme = this.forme.rotation(angle, centre); // Rotation de la forme
    }

    @Override
    public void translation(Vecteur2D deplacement) {
        this.forme = this.forme.deplace(deplacement); // Déplacement de la forme
    }
}
