package fr.rob4.simulation.geometrie;

import java.util.Objects;

import fr.rob4.simulation.exception.NonRelatifException;

/**
 * Cette classe represente un Point2D. Il connait son repere d'origine egalement
 * represente par un Point2D. Element principale des Formes
 * 
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Vecteur2D
 * @see Forme
 * @see NonRelatifException
 */
public class Point2D {
    protected final Point2D origine;
    protected final Vecteur2D position;

    /**
     * Crée un Point2D relativement à son origine
     * 
     * @param origine  Représente le repère d'origine du nouveau point (une valeur
     *                 {@code null} correspond au repère absolu)
     * @param position La position relative
     */
    public Point2D(Point2D origine, Vecteur2D position) {
	this.origine = origine;
	this.position = Objects.requireNonNull(position);
    }

    /**
     * Cree un Point2D dans le repere absolu
     * 
     * @param position La postion dans le repere absolu
     * @see Point2D#Point2D(double, double)
     */
    public Point2D(Vecteur2D position) {
	this(null, position);
    }

    /**
     * Obtient l'origine du repere relatif
     * 
     * @return L'origine du repere relatif
     * @throws NonRelatifException Quand le reere est absolu
     */
    public Point2D getOrigine() throws NonRelatifException {
	if (origine == null) {
	    throw new NonRelatifException(this);
	}
	return origine;
    }

    /**
     * Obtient la position du point2D par rapport à son origine
     * 
     * @return position La position du point2D par rapport à son origine
     * @see Point2D#getPositionRelative(Point2D)
     */
    public Vecteur2D getPositionRelative() {
	return position;
    }

    /**
     * Obtient la position dans le repère absolu
     * 
     * @return La position dans le repère absolu
     * @see Point2D#getPositionRelative()
     */
    public Vecteur2D getPositionAbsolue() {
	if (origine == null) {
	    return position;
	}
	return position.addition(origine.getPositionAbsolue());
    }

    /**
     * Obtient le vecteur partant de ce point vers {@code p}
     *
     * @param p Le point
     * @return Le vecteur partant de ce point vers {@code p}
     */
    public Vecteur2D getPositionRelative(Point2D p) {
	Vecteur2D posAbs = getPositionAbsolue();
	Vecteur2D pPosAbs = Objects.requireNonNull(p).getPositionAbsolue();
	return pPosAbs.soustraction(posAbs);
    }

    /**
     * Crée une copie et la déplace
     * <p>
     * NOTE: L'instance n'est pas modifié
     * 
     * @param v Le déplacement
     * @return Une copie du Point2D déplacé de {@code v}
     */
    public Point2D deplace(Vecteur2D v) {
	return new Point2D(origine, position.addition(v));
    }

    @Override
    public String toString() {
	return "Point2D{" + "origine=" + origine + ", position=" + position + '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	Point2D point2D = (Point2D) o;
	return Objects.equals(origine, point2D.origine) && Objects.equals(position, point2D.position);
    }

    @Override
    public int hashCode() {
	return Objects.hash(origine, position);
    }

    @Override
    public Point2D clone() {
	try {
	    // On retourne la copie peu profonde car Point2D est immutable
	    return (Point2D) super.clone();
	} catch (CloneNotSupportedException e) {
	    return null;
	}
    }

    /**
     * Fait tourner le point de alpha autour de son origine.
     * 
     * @param alpha Angle de la rotation.
     */
    public Point2D rotationOrigine(double alpha) {
	Vecteur2D newPos = position.rotation(alpha);
	return new Point2D(origine, newPos);
    }

    public Point2D rotation(double alpha, Point2D p) {
	Objects.requireNonNull(p, "Ne peut pas faire de rotation sans Point");
	// Position d'entrainement par p
	Vecteur2D posE = p.getPositionAbsolue();
	// Position relative à p
	Vecteur2D posR = p.getPositionRelative(this);
	Vecteur2D newPosR = posR.rotation(alpha);
	// Position absolue
	Vecteur2D newPosA = posE.addition(newPosR);
	// Point après rotation
	Point2D pRot = new Point2D(newPosA);
	if (origine != null) {
	    // L'instance est relative à un point
	    Vecteur2D newPois = origine.getPositionRelative(pRot);
	    pRot = new Point2D(origine, newPois);
	}
	return pRot;
    }
}
