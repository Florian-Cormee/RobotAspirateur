package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.Robot;
import fr.rob4.simulation.element.module.IModule;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Point2D;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.geometrie.Segment;
import fr.rob4.simulation.geometrie.Vecteur2D;
import fr.rob4.simulation.strategie.IStrategie;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.element.module.CapteurDessinable;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Collection;
import java.util.Collections;

public class RobotDessinable extends Robot implements IDessinable {

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
    public RobotDessinable(Forme forme,
                           Collection<? extends IModule<?>> modules,
                           IStrategie strategie,
                           double theta,
                           double ecartRoues) {
        super(forme, modules, strategie, theta, ecartRoues);
    }

    @Override
    public String toString() {
        return "RobotDessinable{} " + super.toString();
    }

    @Override
    public void dessine(Graphics2D graphics2D, double echelle) {
        Color precColor = graphics2D.getColor();
        Stroke precStroke = graphics2D.getStroke();

        graphics2D.setColor(Color.PINK);
        IDessinateur<Forme> formeDess = GeometrieDessinateurFactory.instance.forme();
        formeDess.dessine(graphics2D, echelle, true, this.forme);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new BasicStroke(2));
        Rectangle dim = this.forme.getDimension();
        Point2D centre = this.forme.getCentre();
        double longueur = Math.min(dim.getHauteur(), dim.getLargeur()) / 2;
        Point2D extremite = new Point2D(centre, new Vecteur2D(longueur, 0));
        extremite = extremite.rotationOrigine(this.theta);
        Segment direction = new Segment(centre, extremite);
        IDessinateur<Segment> segDess = GeometrieDessinateurFactory.instance.segment();
        segDess.dessine(graphics2D, echelle, false, direction);

        graphics2D.setColor(precColor);
        graphics2D.setStroke(precStroke);

        for (IModule<?> module : this.getModules()) {
            if (module instanceof IDessinable) {
                ((IDessinable) module).dessine(graphics2D, echelle);
            } else {
                new CapteurDessinable<>(module, false);
            }
        }
    }
}
