package fr.rob4.simulation.element;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;

import java.util.*;

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
     * {@code null} et normalise le vecteur direction donné.
     * <p>
     * Une collection de capteur {@code null} crée une liste de capteurs vide
     *
     * @param forme      La forme
     * @param capteurs   Les capteurs embarqués
     * @param strategie  La strategie que adopté
     * @param theta      L'angle initial
     * @param ecartRoues La distance en mètres séparant les roues
     *
     * @see Collections#emptyList()
     */
    public Robot(Forme forme,
                 Collection<? extends IModule<?>> capteurs,
                 IStrategie strategie,
                 double theta,
                 double ecartRoues) {
        super(forme);
        this.strategie = Objects.requireNonNull(strategie);
        this.theta = theta;
        if (capteurs == null) {
            this.modules = Collections.emptyList();
        } else {
            this.modules = new ArrayList<>(capteurs);
        }
        this.ecartRoues = ecartRoues;
        nettoie = false;
    }

    /**
     * Actualise les modules puis la strategie
     */
    @Override
    public void actualise(Simulation simulation) {
        for (IModule<?> module : modules) {
            module.actualise(simulation);
        }
        strategie.decide(this);
    }

    @Override
    public void deplace(double dG, double dD) {
        // Limitation du déplacement des roues
        dG = Math.min(dG, VITESSE_MAX * Simulation.T);
        dD = Math.min(dD, VITESSE_MAX * Simulation.T);
        // Déplacement du robot (cf. code fourni dans Position.java)
        double alpha = (dD - dG) / ecartRoues;
        Vecteur2D deplacement;
        if (Math.abs(alpha) > 1e-20) {
            // Rotation non négligeable
            double r = (dG / alpha) + (ecartRoues / 2);
            double dx = r * (Math.cos(alpha) - 1);
            double dy = r * Math.sin(alpha);
            deplacement = new Vecteur2D(dx, dy);
            deplacement = deplacement.rotation(theta - Math.PI / 2);
        } else {
            // Rotation négligeable
            double dx = dG * Math.cos(theta);
            double dy = dG * Math.sin(theta);
            deplacement = new Vecteur2D(dx, dy);
            alpha = 0;
        }
        // Mise à jour de la position et de l'orientation
        Point2D pos = getPosition().deplace(deplacement);
        setPosition(pos);
        theta += alpha;
    }

    /**
     * Obtient la postion
     *
     * @return La position
     */
    public Point2D getPosition() {
        return forme.getCentre();
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
        dernierePos = getPosition();
        forme.setCentre(pos);
    }

    @Override
    public List<IModule<?>> getModules() {
        return Collections.unmodifiableList(modules);
    }

    @Override
    public <T extends IModule<?>> List<T> getModules(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (IModule<?> module : modules) {
            if (c.isInstance(module)) {
                list.add(c.cast(module));
            }
        }
        return list;
    }

    @Override
    public IStrategie getStrategie() {
        return strategie;
    }

    @Override
    public boolean isNettoie() {
        return nettoie;
    }

    @Override
    public void setNettoie(boolean etat) {
        nettoie = etat;
    }

    @Override
    public void gereCollision(ICollisionable element) {
        // Annulation du dernier déplacement
        setPosition(dernierePos);
    }

    @Override
    public String toString() {
        return "Robot [modules=" + modules + ", strategie=" + strategie + ", theta=" + theta + ", toString()=" + super.toString() + "]";
    }
}
