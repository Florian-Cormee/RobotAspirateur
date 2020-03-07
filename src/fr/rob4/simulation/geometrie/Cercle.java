/**
 *
 */
package fr.rob4.simulation.geometrie;

import fr.rob4.simulation.exception.NoIntersectionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe représente un Cercle paramétré par son centre et son rayon.
 * Classe fille de Forme.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Vecteur2D
 * @see Forme
 * @see ArcDeCercle
 * @see Polygone
 * @see Rectangle
 */
public class Cercle extends Forme {

    // Attribut
    protected double rayon;

    /**
     * Crée un nouveau Cercle à partir de son centre et de son rayon.
     *
     * @param p Centre
     * @param d rayon
     */
    public Cercle(Point2D p, double d) {
        super(p);
        this.rayon = d;
    }

    /**
     * Crée un nouveau cercle à partir des coordonnées du centre et du diamètre.
     *
     * @param x Abscisse du centre.
     * @param y Ordonnée du centre.
     * @param d rayon
     */
    public Cercle(double x, double y, double d) {
        super(x, y);
        this.rayon = d;
    }

    @Override
    public boolean collisionne(Forme f) throws NoIntersectionException {
        // On teste d'abord si les formes sont assez proches
        try {
            this.getDimension().intersecte(f.getDimension());
        } catch (NoIntersectionException e) {
            e.printStackTrace();
            return false;
        }
        if (f.getClass() == Cercle.class) {
            Cercle c = (Cercle) f;
            try {
                this.intersecte(c);
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
            Polygone g = (Polygone) f;
            try {
                this.intersecte(g);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (f.getClass() == ArcDeCercle.class) {
            ArcDeCercle adc = (ArcDeCercle) f;
            try {
                this.intersecte(adc);
                return true;
            } catch (NoIntersectionException e) {
                e.printStackTrace();
                return false;
            }
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
        throw new NoIntersectionException(this, "Ce cercle n'a pas de collision. Ou la forme n'est pas connue.");
    }

    @Override
    public Rectangle getDimension() {
        return new Rectangle(this.centre, this.rayon * 2, this.rayon * 2);
    }

    @Override
    public Cercle rotation(double alpha, Point2D p) {
        Vecteur2D newPos = p.getPositionRelative(this.centre).rotation(alpha).addition(p.position);
        // Cercle newCercle = new Cercle(new Point2D(centre.origine, newPos),rayon);
        return new Cercle(new Point2D(this.centre.origine, newPos), this.rayon);
    }

    @Override
    public Cercle deplace(Vecteur2D v) {
        return new Cercle(getCentre().deplace(v), rayon);
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance de cercle et un
     * autre cercle mis en argument.
     *
     * @param cercle Cercle avec lequel on test les collisions.
     * @return Liste de point2D étant des points d'interection.
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(Cercle cercle) throws NoIntersectionException {
        if (this.centre.getPositionRelative(cercle.centre).norme() <= this.rayon + cercle.rayon) {
            List<Point2D> liste = new ArrayList<Point2D>();
            Vecteur2D obsPos = cercle.centre.getPositionAbsolue();
            double x0 = this.centre.getPositionAbsolue().x;
            double y0 = this.centre.getPositionAbsolue().y;
            double x1 = obsPos.x;
            double y1 = obsPos.y;
            double coeff = (x0 - x1) / (y0 - y1);
            double n = (Math.pow(cercle.rayon,
                                 2) - this.rayon * this.rayon - x1 * x1 + x0 * x0 - y1 * y1 + y0 * y0) / (2 * (y0 - y1));
            double a = coeff * coeff + 1;
            double b = 2 * y0 * coeff - 2 * n * coeff - 2 * x0;
            double c = x0 * x0 + y0 * y0 * n * n - this.rayon * this.rayon - 2 * y0 * n;

            double delta = Math.sqrt(b * b - 4 * a * c);
            double xp = (-b - delta) / (2 * a);
            double yp = n - xp * coeff;

            if (delta == 0) {
                liste.add(new Point2D(new Vecteur2D(xp, yp)));
                return liste;
            }

            double xq = (-b + delta) / (2 * a);
            double yq = n - xq * coeff;

            liste.add(new Point2D(new Vecteur2D(xp, yp)));
            liste.add(new Point2D(new Vecteur2D(xq, yq)));

            return liste;
        }
        throw new NoIntersectionException(this, "Pas d'intersection entre les cercles.");
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance de cercle et un
     * rectangle mis en argument.
     *
     * @param r Rectangle avec lequel on teste les intersections.
     * @return Liste des points d'intersection.
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(Rectangle r) throws NoIntersectionException {
        return this.intersecte(r.toPolygone());
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance de cercle et un
     * polygone mis en argument.
     *
     * @param pol Polygone avec lequel on teste les intersections.
     * @return Liste des points d'intersection.
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(Polygone pol) throws NoIntersectionException {
        List<Point2D> liste = new ArrayList<Point2D>();
        for (Segment s : pol.getSegments()) {
            try {
                liste.addAll(s.intersecte(this));
            } catch (NoIntersectionException e) {
                continue;
            }
        }
        if (liste.size() == 0) {
            throw new NoIntersectionException(this, "Pas d'intersection entre le cercle et le rectangle");
        }
        return liste;
    }

    /**
     * Obtient la liste de points d'intersection entre l'instance de cercle et un
     * arc de cercle mis en argument.
     *
     * @param adc Arc de cercle avec lequel on teste les intersections.
     * @return Liste des points d'intersection.
     * @throws NoIntersectionException
     */
    List<Point2D> intersecte(ArcDeCercle adc) throws NoIntersectionException {
        try {
            List<Point2D> liste = this.intersecte(new Cercle(adc.centre, adc.rayon));
            Vecteur2D x = new Vecteur2D(1, 0);
            for (Point2D p : liste) {
                Vecteur2D test = adc.centre.getPositionRelative(p);
                if (adc.ang1 <= adc.ang2) {
                    if (test.angle(x) < adc.ang1 || test.angle(x) > adc.ang2) {
                        liste.remove(p);
                    }
                } else {
                    if (test.angle(x) < adc.ang1 && test.angle(x) > adc.ang2) {
                        liste.remove(p);
                    }
                }
            }
            if (liste.size() == 0) {
                throw new NoIntersectionException(this,
                                                  "L'intersection entre le cercle et l'arc de cercle ne se fait pas " +
                                                          "sur l'arc de cercle.");
            } else {
                return liste;
            }
        } catch (NoIntersectionException e) {
            throw new NoIntersectionException("Pas d'intersection entre le cercle et l'arc de cercle", e, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Cercle cercle = (Cercle) o;
        return Objects.equals(this.centre, cercle.centre) && (this.rayon == cercle.rayon);
    }

    @Override
    public Cercle clone() {
        try {
            return (Cercle) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Cercle [rayon=" + this.rayon + ", centre=" + this.centre + "]";
    }

    /**
     * Obtient le rayon
     *
     * @return Le rayon
     */
    public double getRayon() {
        return this.rayon;
    }
}
