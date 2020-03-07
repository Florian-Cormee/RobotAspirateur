package fr.rob4.simulation;

import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IElement;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.element.IRobot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.ArcDeCercle;
import fr.rob4.simulation.geometrie.Cercle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SimulationStdBuilder {
    public static final double LARGEUR_PIECE = 4;
    public static final double HAUTEUR_PIECE = 4;
    public static final double DIAMETRE_ROBOT_STD = 0.34;
    public static final double DIAMETRE_CAPTEUR_SALETE_STD = 0.05;
    public static final double OUVERTURE_CAPTEUR_CONTACT_STD = Math.toRadians(52);
    public static final double ESPACEMENT_CAPTEUR_CONTACT_STD = Math.toRadians(4);

    private final List<IElement> elements;
    private final ElementFactory factory;
    private final ICollisionable bordures;

    /**
     * Crée un builder de simulation selon le cahier des charges
     */
    public SimulationStdBuilder(ElementFactory factory) {
        this.factory = Objects.requireNonNull(factory);
        this.bordures = factory.obstacle(new Rectangle(0, 0, LARGEUR_PIECE, HAUTEUR_PIECE));
        this.elements = new LinkedList<>();
    }

    /**
     * Crée un obstacle circulaire au coordonnées spécifiées
     *
     * @param x        L'absisse
     * @param y        L'ordonnée
     * @param diametre Le diamètre de la tache
     */
    public void ajouteObstacleCirculaire(double x, double y, double diametre) {
        // Test les arguments
        if (this.estHorsZone(x, y, diametre, diametre)) {
            throw new IllegalArgumentException("Les coordonnées font sortir l'obstacle de la simulation");
        }
        diametre = Math.abs(diametre);
        if (diametre > DIAMETRE_ROBOT_STD) {
            throw new IllegalArgumentException("La tache doit être plus petite que le robot");
        }
        // Crée la tache
        Cercle forme = new Cercle(x, y, diametre / 2);
        ICollisionable tache = this.factory.obstacle(forme);
        this.ajouteElement(tache);
    }

    private boolean estHorsZone(double x, double y, double largeur, double hauteur) {
        return (Math.abs(x) > (LARGEUR_PIECE - largeur) / 2) || (Math.abs(y) > (HAUTEUR_PIECE - hauteur) / 2);
    }

    private void ajouteElement(IElement element) {
        Objects.requireNonNull(element);
        this.elements.add(element);
    }

    /**
     * Ajoute un robot circulaire possédant 3 capteurs de contact et un capteur de saleté
     *
     * @param x           L'absisse du robot
     * @param y           L'ordonnée du robot
     * @param orientation L'orientation du robot
     * @param strategie   La stratégie du robot
     */
    public void ajouteRobotStandard(double x, double y, double orientation, IStrategie strategie) {
        // Vérification des arguments
        if (this.estHorsZone(x, y, DIAMETRE_ROBOT_STD, DIAMETRE_ROBOT_STD)) {
            throw new IllegalArgumentException("Les coordonnées font sortir le robot de la simulation");
        }
        Objects.requireNonNull(strategie);
        // Création des éléments nécessaires au robot
        Point2D centre = new Point2D(new Vecteur2D(x, y));
        Forme formeRobot = new Cercle(centre, DIAMETRE_ROBOT_STD / 2);
        IModule<?>[] modules = this.creeCapteursStd(centre, orientation);
        IRobot robot = this.factory.robot(formeRobot, strategie, orientation, DIAMETRE_ROBOT_STD / 2, modules);
        this.ajouteElement(robot);
    }

    /**
     * Crée les capteurs pour un robot standard
     *
     * @param centre      Le centre du robot
     * @param orientation L'orientation du robot
     *
     * @return Un tableau des capteurs d'un robot standard
     */
    private IModule<?>[] creeCapteursStd(Point2D centre, double orientation) {
        // Création du capteur de saleté
        Forme formeCapteurSalete = new Cercle(centre, DIAMETRE_CAPTEUR_SALETE_STD / 2);
        IModule<Boolean> capteurSalete = this.factory.capteurSalete(formeCapteurSalete);
        // Création de la zone de détection du capteur de contact central
        Forme formeCapteurContact = new ArcDeCercle(centre,
                                                    DIAMETRE_ROBOT_STD / 2 + 0.01,
                                                    -OUVERTURE_CAPTEUR_CONTACT_STD / 2,
                                                    +OUVERTURE_CAPTEUR_CONTACT_STD / 2);
        // Création des capteurs de contact
        IModule<Boolean> capteurContactG = this.factory.capteurContact(formeCapteurContact);
        capteurContactG.rotation(orientation - OUVERTURE_CAPTEUR_CONTACT_STD - ESPACEMENT_CAPTEUR_CONTACT_STD, centre);
        IModule<Boolean> capteurContactC = this.factory.capteurContact(formeCapteurContact);
        capteurContactC.rotation(orientation, centre);
        IModule<Boolean> capteurContactD = this.factory.capteurContact(formeCapteurContact);
        capteurContactD.rotation(orientation + OUVERTURE_CAPTEUR_CONTACT_STD + ESPACEMENT_CAPTEUR_CONTACT_STD, centre);
        return new IModule<?>[]{capteurSalete, capteurContactG, capteurContactC, capteurContactD};
    }

    public void ajouteTacheCirculaire(double x, double y, double diametre) {
        if (this.estHorsZone(x, y, diametre, diametre)) {
            throw new IllegalArgumentException("Les coordonnées font sortir la tache de la simulation");
        }
        Cercle forme = new Cercle(x, y, diametre / 2);
        INettoyable tache = this.factory.tache(forme);
        this.ajouteElement(tache);
    }

    public Simulation build() {
        return new Simulation(this.bordures, this.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.elements, this.factory, this.bordures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SimulationStdBuilder builder = (SimulationStdBuilder) o;
        return Objects.equals(this.elements, builder.elements) &&
               Objects.equals(this.factory, builder.factory) &&
               Objects.equals(this.bordures, builder.bordures);
    }

    @Override
    public String toString() {
        return "SimulationStdBuilder{" +
               "elements=" +
               elements +
               ", factory=" +
               factory +
               ", bordures=" +
               bordures +
               '}';
    }
}
