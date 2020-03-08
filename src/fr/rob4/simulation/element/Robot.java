package fr.rob4.simulation.element;

import fr.rob4.simulation.Outil;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Robot extends Element implements IRobot {
    /** Vitesse maximale des robots (en m/s) */
    public static final double VITESSE_MAX = 1;

    protected List<IModule<?>> modules;
    protected IStrategie strategie;
    protected double theta;
    protected double ecartRoues;
    protected boolean nettoie;
    protected Point2D dernierePos;
    protected double dernierTheta;

    /**
     * Crée un robot
     * <p>
     * S'assure que les arguments, hormis la collection de capteurs, ne sont pas
     * {@code null} et normalise le vecteur direction donné. <br/>
     * Une collection de capteur {@code null} crée une liste de capteurs vide
     *
     * @param forme      La forme
     * @param modules    Les modules embarqués
     * @param strategie  La strategie que adopté
     * @param theta      L'angle initial
     * @param ecartRoues La distance en mètres séparant les roues
     *
     * @see Collections#emptyList()
     */
    public Robot(Forme forme,
                 Collection<? extends IModule<?>> modules,
                 IStrategie strategie,
                 double theta,
                 double ecartRoues) {
        super(forme);
        this.strategie = Objects.requireNonNull(strategie);
        this.theta = theta;
        if (modules == null) {
            this.modules = Collections.emptyList();
        } else {
            this.modules = new ArrayList<>(modules);
        }
        this.ecartRoues = ecartRoues;
        this.nettoie = false;
    }

    @Override
    public void actualise(Simulation simulation, Object appeleur) {
        this.strategie.decide(this);
        // Actualise chaque capteur
        for (IModule<?> module : this.modules) {
            module.actualise(simulation, this);
        }
    }

    @Override
    public void deplace(double dG, double dD) {
        // Limitation du déplacement des roues
        double dGl = Math.signum(dG) * Math.min(Math.abs(dG), VITESSE_MAX * Simulation.T);
        double dDl = Math.signum(dD) * Math.min(Math.abs(dD), VITESSE_MAX * Simulation.T);
        // Déplacement du robot (cf. code fourni dans Position.java)
        double alpha = (dDl - dGl) / this.ecartRoues;
        Vecteur2D deplacement;
        if (Math.abs(alpha) > 1e-20) {
            // Rotation non négligeable
            double r = (dGl / alpha) + (this.ecartRoues / 2);
            double dx = r * (Math.cos(alpha) - 1);
            double dy = r * Math.sin(alpha);
            deplacement = new Vecteur2D(dx, dy);
            deplacement = deplacement.rotation(this.theta - Math.PI / 2);
        } else {
            // Rotation négligeable
            double dx = dGl * Math.cos(this.theta);
            double dy = dGl * Math.sin(this.theta);
            deplacement = new Vecteur2D(dx, dy);
            alpha = 0;
        }
        // Mise à jour de la position et de l'orientation
        this.translation(deplacement);
        Point2D centre = this.getPosition();
        this.rotation(alpha, centre);
    }

    @Override
    public List<IModule<?>> getModules() {
        return Collections.unmodifiableList(this.modules);
    }

    @Override
    public <T extends IModule<?>> List<T> getModules(Class<? extends T> c) {
        List<T> list = new ArrayList<>();
        for (IModule<?> module : this.modules) {
            if (c.isInstance(module)) {
                T moduleCast = c.cast(module);
                list.add(moduleCast);
            }
        }
        return list;
    }

    @Override
    public IStrategie getStrategie() {
        return this.strategie;
    }

    @Override
    public boolean isNettoie() {
        return this.nettoie;
    }

    @Override
    public void setNettoie(boolean etat) {
        this.nettoie = etat;
    }

    @Override
    public void translation(Vecteur2D deplacement) {
        this.dernierePos = this.getPosition();
        super.translation(deplacement);
        // Déplace tous les capteurs
        for (IModule<?> module : this.modules) {
            module.translation(deplacement);
        }
    }

    @Override
    public Point2D getPosition() {
        return this.forme.getCentre();
    }

    @Override
    public void rotation(double angle, Point2D centre) {
        this.dernierTheta = this.theta;
        super.rotation(angle, centre);
        this.theta = Outil.normalize_angle(this.theta + angle);
        for (IModule<?> module : this.modules) {
            module.rotation(angle, centre);
        }
    }

    @Override
    public double getOrientation() {
        return this.theta;
    }

    @Override
    public void gereCollision(ICollisionable element) {
        // Annulation du dernier déplacement
        if (this.dernierePos != null) {
            Vecteur2D dernierePosAbsolue = this.dernierePos.getPositionAbsolue();
            Vecteur2D positionAbsolue = this.getPosition().getPositionAbsolue();
            Vecteur2D deplacementInv = dernierePosAbsolue.soustraction(positionAbsolue);
            this.translation(deplacementInv);
            this.rotation(this.dernierTheta - this.theta, this.getPosition());
        }
    }

    @Override
    public int hashCode() {
        int superCode = super.hashCode();
        return Objects.hash(superCode,
                            this.modules,
                            this.strategie,
                            this.theta,
                            this.ecartRoues,
                            this.nettoie,
                            this.dernierePos,
                            this.dernierTheta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Robot robot = (Robot) o;
        return Double.compare(robot.theta, this.theta) == 0 &&
               Double.compare(robot.ecartRoues, this.ecartRoues) == 0 &&
               this.nettoie == robot.nettoie &&
               Double.compare(robot.dernierTheta, this.dernierTheta) == 0 &&
               Objects.equals(this.modules, robot.modules) &&
               Objects.equals(this.strategie, robot.strategie) &&
               Objects.equals(this.dernierePos, robot.dernierePos);
    }

    @Override
    public String toString() {
        return "Robot [modules=" +
               this.modules +
               ", strategie=" +
               this.strategie +
               ", theta=" +
               this.theta +
               ", " +
               "super.toString()=" +
               super.toString() +
               "]";
    }
}
