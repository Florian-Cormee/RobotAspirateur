package fr.rob4.simulation.strategie;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.element.module.CapteurContact;

import java.util.List;
import java.util.Objects;

public class StrategieAleatoire implements IStrategie {
    private double angleCible;
    protected double distArr;
    private double kRot;
    private Etats etat;

    public StrategieAleatoire() {
        this.angleCible = 0;
        this.distArr = 0;
        this.etat = Etats.AVANCE;
        this.kRot = 0.05;
    }

    @Override
    public void decide(IRobot robot) {
        robot.setNettoie(true);
        if (this.etat != Etats.TOURNE) {
            // Vérifie s'il y a une collision
            List<CapteurContact> capteurContacts = robot.getModules(CapteurContact.class);
            for (CapteurContact cc : capteurContacts) {
                // En cas de collision, on recule
                if (cc.getInfo()) {
                    this.etat = Etats.RECUL;
                    this.distArr = 0;
                }
            }
        }

        if (this.etat == Etats.AVANCE) {
            // Déplace le robot en ligne droite
            robot.deplace(2, 2);
        } else if (this.etat == Etats.RECUL) {
            robot.deplace(-2, -2);
            this.distArr += Robot.VITESSE_MAX * Simulation.T;
            if (this.distArr >= 0.05) {
                // on choisi une direction de fuite aléatoire
                this.angleCible = (Math.random() - 0.5) * 2 * Math.PI;
                this.etat = Etats.TOURNE;
            }
        } else if (this.etat == Etats.TOURNE) {
            // Fait tourner le robot jusqu'à l'orientation désirée
            double DeltaRoues = this.kRot * (this.angleCible - robot.getOrientation());
            robot.deplace(-DeltaRoues / 2, DeltaRoues / 2);
            if (Math.abs(this.angleCible - robot.getOrientation()) < 1e-3) {
                this.etat = Etats.AVANCE;
            }
        }
    }

    /**
     * Obtient l'orientation ciblée
     *
     * @return L'orientation ciblée (en radian)
     */
    public double getAngleCible() {
        return this.angleCible;
    }

    /**
     * Obtient l'état courant
     *
     * @return L'état courant
     */
    public Etats getEtat() {
        return this.etat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.angleCible, this.distArr, this.kRot, this.etat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StrategieAleatoire that = (StrategieAleatoire) o;
        return Double.compare(that.angleCible, this.angleCible) == 0 && Double.compare(that.distArr, this.distArr) ==
                                                                        0 && Double.compare(that.kRot, this.kRot) ==
                                                                             0 && this.etat == that.etat;
    }

    @Override
    public String toString() {
        return "StrategieAleatoire[" +
               "angleCible=" +
               this.angleCible +
               ", distArr=" +
               this.distArr +
               ", kRot=" +
               this.kRot +
               ", etat=" +
               this.etat +
               ']';
    }

    public enum Etats {
        TOURNE,
        AVANCE,
        RECUL
    }
}
