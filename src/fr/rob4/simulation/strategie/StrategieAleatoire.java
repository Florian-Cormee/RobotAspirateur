package fr.rob4.simulation.strategie;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.element.module.CapteurContact;

import java.util.List;

public class StrategieAleatoire implements IStrategie {
    private double angleCible;
    private double distArr;
    private double kRot;
    private Etats etat;

    public StrategieAleatoire() {
        this.angleCible = 0;
        this.distArr = 0;
        this.etat = Etats.AVANCE;
        this.kRot = 0.05;
    }

    @Override
    public boolean decide(IRobot robot) {
        if (this.etat != Etats.TOURNE) {
            // Vérifie s'il y a une collision
            List<CapteurContact> capteurContacts = robot.getModules(CapteurContact.class);
            for (CapteurContact cc : capteurContacts) {
                // En cas de collision, on choisi une direction de fuite aléatoire
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
            if (this.distArr > 0.05) {
                this.angleCible = (Math.random() - 0.5) * 2 * Math.PI;
                this.etat = Etats.TOURNE;
            }
        } else if (this.etat == Etats.TOURNE) {
            // Fait tourner le robot jusqu'à l'orientation désirée
            double DeltaRoues = this.kRot * (this.angleCible - robot.getOrientation());
            robot.deplace(-DeltaRoues / 2, DeltaRoues / 2);
            if (Math.abs(this.angleCible - robot.getOrientation()) < 1e-1) {
                this.etat = Etats.AVANCE;
            }
        }
        return true;
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

    public enum Etats {
        TOURNE,
        AVANCE,
        RECUL
    }

}
