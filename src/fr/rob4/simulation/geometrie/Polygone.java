package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Cette Classe représente un polygone. Il peut avoir autant de point que
 * souhaité.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see Cercle
 * @see ArcDeCercle
 * @see Rectangle
 */
public class Polygone extends Forme {

    // Attributs
    protected List<Point2D> points;

    /**
     * Crée un polygone à partir de son centre et d'une liste de Point2D
     * représentant ses sommets.
     *
     * @param p  Centre
     * @param cp Liste des sommets (Collection)
     */
    public Polygone(Point2D p, Collection<? extends Point2D> cp) {
        super(p);
        this.points = new ArrayList<>(cp);
    }

    /**
     * Crée un polygone à partir des coordonnées de son centre et d'une liste de
     * Point2D représentant ses sommets.
     *
     * @param x  Abscisse du centre.
     * @param y  Ordonnée du centre.
     * @param cp Liste des sommets (Collection)
     */
    public Polygone(double x, double y, Collection<? extends Point2D> cp) {
        super(x, y);
        this.points = new ArrayList<>(cp);
    }

    @Override
    public boolean collisionne(Forme f) throws NoIntersectionException {
        // On teste d'abord si les formes sont assez proches
        try {
            Rectangle dimension = f.getDimension();
            Rectangle thisDimension = this.getDimension();
            thisDimension.intersecte(dimension);
        } catch (NoIntersectionException e) {
            e.printStackTrace();
            return false;
        }

        if (f.getClass() == Segment.class) {
            Segment s = (Segment) f;
            try {
                s.intersecte(this);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (f.getClass() == Cercle.class) {
            Cercle c = (Cercle) f;
            try {
                c.intersecte(this);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (f.getClass() == ArcDeCercle.class) {
            ArcDeCercle adc = (ArcDeCercle) f;
            try {
                adc.intersecte(this);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (f.getClass() == Rectangle.class) {
            Rectangle r = (Rectangle) f;
            try {
                this.intersecte(r);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (f.getClass() == Polygone.class) {
            Polygone pol = (Polygone) f;
            try {
                this.intersecte(pol);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        throw new NoIntersectionException(this, "Ce polygone n'a pas de collision. Ou la forme n'est pas connue.");
    }

    @Override
    public Rectangle getDimension() {
        double xMax = Double.NEGATIVE_INFINITY;
        double xMin = Double.POSITIVE_INFINITY;
        double yMax = Double.NEGATIVE_INFINITY;
        double yMin = Double.POSITIVE_INFINITY;

        for (Point2D p : this.points) {
            Vecteur2D vTemp = p.getPositionAbsolue();
            xMax = Math.max(xMax, vTemp.x);
            xMin = Math.min(xMin, vTemp.x);
            yMax = Math.max(yMax, vTemp.y);
            yMin = Math.min(yMin, vTemp.y);
        }

        return new Rectangle(this.centre, xMax - xMin, yMax - yMin);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.rob4.simulation.geometrie.Forme#rotation(double,
     * fr.rob4.simulation.geometrie.Point2D)
     */
    @Override
    public Polygone rotation(double alpha, Point2D p) {
        List<Point2D> newPoints = new ArrayList<>();
        for (Point2D point : this.points) {
            Vecteur2D newPos = p.getPositionRelative(point).rotation(alpha).addition(p.position);
            newPoints.add(new Point2D(point.origine, newPos));
        }
        Point2D clone = this.centre.clone();
        return new Polygone(clone, newPoints);
    }

    @Override
    public Polygone deplace(Vecteur2D v) {
        List<Point2D> newL = new ArrayList<Point2D>();
        for (Point2D p : getPoints()) {
            newL.add(p.deplace(v));
        }
        return new Polygone(getCentre().deplace(v), newL);
    }

    /**
     * Obtient la liste des sommets.
     *
     * @return Liste des sommets.
     */
    public List<Point2D> getPoints() {
        return Collections.unmodifiableList(this.points);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Polygone polygone = (Polygone) o;
        return Objects.deepEquals(this.points.toArray(), polygone.points.toArray()) && Objects.equals(this.centre,
                                                                                                      polygone.centre);
    }

    @Override
    public String toString() {
        return "Polygone [points=" + this.points + ", centre=" + this.centre + "]";
    }

    public List<Segment> getSegments() {
        List<Segment> liste = new ArrayList<>();
        Point2D prem = this.points.get(0);
        Point2D pt1 = prem;
        // Point2D pt2 = points.get(1);
        for (Point2D p : this.points) {
            if (p.equals(prem)) {
                continue;
            }
            liste.add(new Segment(pt1, p));
            pt1 = p;
        }
        liste.add(new Segment(pt1, prem));
        return liste;
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance d'un polygone et
     * un rectangle mis en argument.
     *
     * @param r Rectangle avec lequel on teste l'intersection.
     *
     * @return Liste des points d'intersection.
     *
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
        try {
            Polygone polygone = r.toPolygone();
            return this.intersecte(polygone);
        } catch (NoIntersectionException e) {
            e.printStackTrace();
            throw new NoIntersectionException("Pas d'intersection entre le polygone et le rectangle", e, this);
        }
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance d'un polygone et
     * un polygone mis en argument.
     *
     * @param pol Polygone avec lequel on test l'intersection.
     *
     * @return Liste des points d'intersection.
     *
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
        List<Point2D> liste = new ArrayList<>();
        List<Point2D> listPtColl;
        for (Segment s : this.getSegments()) {
            try {
                listPtColl = s.intersecte(pol);
                for (Point2D p : listPtColl) {
                    if (!liste.contains(p)) {
                        liste.add(p);
                    }
                }
            } catch (NoIntersectionException e) {
            }
        }
        if (liste.size() != 0) {
            return liste;
        } else {
            throw new NoIntersectionException(this, "Pas d'intersection entre ce polygone et l'autre.");
        }
    }
}
