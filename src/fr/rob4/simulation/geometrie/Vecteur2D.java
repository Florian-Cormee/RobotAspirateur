package fr.rob4.simulation.geometrie;

import java.util.Objects;

/**
 * Cette classe représente un vecteur 2D avec une abscisse et une ordonnée.
 * Permet de position tous les éléments graphique (Forme) dans leur
 * environement.
 *
 * @author Florentin BEROUJON & Florian CORMEE
 * @version 0.0.1
 * @see Point2D
 * @see Forme
 */
public class Vecteur2D implements Cloneable {
    protected final double x;
    protected final double y;

    /**
     * Crée un Vecteur2D nul.
     */
    public Vecteur2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Crée un Vecteur2D de coordonnées : (x,y).
     *
     * @param x L'abscisse
     * @param y L'ordonnée
     */
    public Vecteur2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Additionne avec {@code v}.
     * <p>
     * Note : L'instance n'est pas modifiée.
     *
     * @param v Le vecteur à additionner.
     *
     * @return Le résultat de l'addition dans une nouvelle instance.
     */
    public Vecteur2D addition(Vecteur2D v) {
        return new Vecteur2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Calcule l'angle avec {@code v}.
     *
     * @param v Un vecteur
     *
     * @return L'angle en radian dans l'intervalle ]-pi ; pi]
     *
     * @throws IllegalArgumentException Quand l'instance ou {@code v} est un vecteur
     *                                  nul
     */
    public double angle(Vecteur2D v) {
        if (this.scalaire(this) < 1e-10 || v.scalaire(v) < 1e-10) {
            throw new IllegalArgumentException("On peut pas calculer l'angle avec un vecteur nul");
        }
        double prodNormes = this.norme() * v.norme();
        // Par définition du produit scalaire : s = ||this|| * ||v|| *
        // cos(angle)
        double cosAngle = this.scalaire(v) / prodNormes;
        // Par définition du produit vectoriel : ||pv|| = ||this|| * ||v|| *
        // sin(angle)
        double sinAngle = (this.x * v.y - v.x * this.y) / prodNormes;
        return Math.atan2(sinAngle, cosAngle);
    }

    /**
     * Calcule le produit scalaire avec {@code v}.
     *
     * @param v Un vecteur
     *
     * @return Le produit scalaire avec {@code v}
     */
    public double scalaire(Vecteur2D v) {
        return this.x * v.x + this.y * v.y;
    }

    /**
     * Calcule la norme.
     *
     * @return La norme
     */
    public double norme() {
        return Math.sqrt(this.scalaire(this));
    }

    /**
     * Obtient l'abscisse.
     *
     * @return L'abscisse.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Obtient l'ordonnée
     *
     * @return L'ordonnée
     */
    public double getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vecteur2D vecteur2D = (Vecteur2D) o;
        return this.equals(vecteur2D, 1e-10);
    }

    @Override
    public Vecteur2D clone() {
        try {
            return (Vecteur2D) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Vecteur2D{" + "x=" + this.x + ", y=" + this.y + '}';
    }

    public boolean equals(Vecteur2D v, double epsilon) {
        return Math.abs(this.x - v.x) < epsilon && Math.abs(this.y - v.y) < epsilon;
    }

    /**
     * Crée un vecteur unitaire avec la même direction et même sens.
     * <p>
     * Note : L'instance n'est pas modifiée.
     *
     * @return Un vecteur unitaire avec la même direction et même sens.
     *
     * @throws IllegalArgumentException Quand l'instance est un vecteur nul
     */
    public Vecteur2D normalise() {
        double norme = this.norme();
        if (norme < 1e-10) {
            throw new IllegalArgumentException("Ne peut pas normaliser un vecteur nul");
        }
        return new Vecteur2D(this.x / norme, this.y / norme);
    }

    /**
     * Multiplie avec un scalaire.
     * <p>
     * Note : L'instance n'est pas modifiée.
     *
     * @param coef Le scalaire
     *
     * @return Le résultat du produit dans une nouvelle instance.
     */
    public Vecteur2D produit(double coef) {
        return new Vecteur2D(this.x * coef, this.y * coef);
    }

    public double produitCroix(Vecteur2D v) {
        return this.x * v.y - this.y * v.x;
    }

    /**
     * Fait tourner le vecteur autour de l'axe normale au plan 2D passant par (0,0).
     * <p>
     * Note : L'instance n'est pas modifiée.
     *
     * @param angle L'angle de rotation
     *
     * @return Le vecteur tourné dans une nouvelle instance
     */
    public Vecteur2D rotation(double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        return new Vecteur2D(this.x * cosAngle - this.y * sinAngle, this.y * cosAngle + this.x * sinAngle);
    }

    /**
     * Soustrait {@code v}.
     * <p>
     * Note : L'instance n'est pas modifiée.
     *
     * @param v Un vecteur
     *
     * @return Le résultat de la soustraction dans une nouvelle instance.
     */
    public Vecteur2D soustraction(Vecteur2D v) {
        return new Vecteur2D(this.x - v.x, this.y - v.y);
    }
}
