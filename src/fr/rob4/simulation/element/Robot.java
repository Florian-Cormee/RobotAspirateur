package fr.rob4.simulation.element;

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

    /**
     * Actualise les modules puis la strategie
     */
    @Override
    public void actualise(Simulation simulation) {
        for (IModule<?> module : this.modules) {
            module.actualise(simulation);
        }
        this.strategie.decide(this);
    }

    @Override
    public void deplace(double dG, double dD) {
        // Limitation du déplacement des roues
        double dGl = Math.min(dG, VITESSE_MAX * Simulation.T);
        double dDl = Math.min(dD, VITESSE_MAX * Simulation.T);
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
        super.translation(deplacement);
        for (IModule<?> module : this.modules) {
            module.translation(deplacement);
        }
    }

    @Override
    public Point2D getPosition() {
        return this.forme.getCentre();
    }

    @Override
    public double getOrientation() {
        return this.theta;
    }

    /**
     * Définit la nouvelle position
     * <p>
     * S'assure que la position soit non {@code null}<br>
     * Sauvegarde la précédente position
     *
     * @param pos La nouvelle position
     */
    protected void setPosition(Point2D pos) {
        Objects.requireNonNull(pos);
        this.dernierePos = this.getPosition();
        this.forme.setCentre(pos);
    }

    @Override
    public void rotation(double angle, Point2D centre) {
        super.rotation(angle, centre);
        for (IModule<?> module : this.modules) {
            module.rotation(angle, centre);
        }
    }

    @Override
    public void gereCollision(ICollisionable element) {
        // Annulation du dernier déplacement
        this.setPosition(this.dernierePos);
    }

    @Override
    public String toString() {
        return "Robot [modules=" + this.modules + ", strategie=" + this.strategie + ", theta=" + this.theta + ", " +
                       "toString()=" + super.toString() + "]";
    }
}
