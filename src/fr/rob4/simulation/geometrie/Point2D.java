package fr.rob4.simulation.geometrie;

import java.util.Objects;

public class Point2D {
    protected Point2D origine;
    protected Vecteur2D position;

    public Point2D(Point2D origine, Vecteur2D position) {
        this.origine = origine;
        this.position = position;
    }

    public Point2D(Vecteur2D position) {
        this(null, position);
    }

    public Point2D getOrigine() {
        return origine;
    }

    public Vecteur2D getPositionRelative() {
        return position;
    }

    public Vecteur2D getPositionAbsolue() {
        if (origine == null) {
            return position.clone();
        }
        return position.addition(origine.getPositionAbsolue());
    }

    /**
     * Obtient le vecteur partant de ce point vers p
     *
     * @param p Le point
     * @return Le vecteur partant de ce point vers p
     */
    public Vecteur2D getPositionRelative(Point2D p) {
        Vecteur2D posAbs = getPositionAbsolue();
        Vecteur2D pPosAbs = p.getPositionAbsolue();
        return pPosAbs.soustraction(posAbs);
    }

    public Point2D deplace(Vecteur2D v) {
        return new Point2D(origine, position.addition(v));
    }

    @Override
    public String toString() {
        return "Point2D{" + "origine=" + origine + ", position=" + position + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Objects.equals(origine, point2D.origine) &&
                Objects.equals(position, point2D.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origine, position);
    }
}
