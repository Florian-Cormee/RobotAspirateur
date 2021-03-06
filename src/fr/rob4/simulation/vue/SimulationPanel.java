package fr.rob4.simulation.vue;

import fr.rob4.simulation.IObservable;
import fr.rob4.simulation.IObservateur;
import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.element.IElement;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.geometrie.Rectangle;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("serial")
public class SimulationPanel extends JPanel implements IObservateur<Simulation> {
    private final Simulation simulation;

    /**
     * Crée un JPanel représentant graphiquement la simulation
     *
     * @param simulation La simulation à représenter
     */
    public SimulationPanel(Simulation simulation) {
        this.simulation = Objects.requireNonNull(simulation);
        simulation.ajouteObservateur(this);// Observe la simulation pour se mettre à jour
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.simulation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SimulationPanel that = (SimulationPanel) o;
        return this.simulation.equals(that.simulation);
    }

    @Override
    public void notifie(IObservable<Simulation> source) {
        if (this.simulation == source) {
            // Si la source est bien l'instance dessinée, on mets à jour la fenètre
            this.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        // Déplace le centre du repère du coin supérieur gauche au centre du JPanel
        Dimension dimension = this.getSize();
        double largeur = dimension.getWidth();
        double hauteur = dimension.getHeight();
        graphics2D.translate(Math.round(largeur / 2), Math.round(hauteur / 2));
        // Inverse le sens de l'axe des ordonnées
        graphics2D.scale(1, -1);
        // Dessine chaque element
        List<IElement> elements = this.simulation.getElements();
        double echelle = this.echelle();
        for (IElement e : elements) {
            this.dessineElement(e, graphics2D, echelle);
        }
        g.dispose();
    }

    /**
     * Calcule l'échelle pour convertir les mètres en pixels
     *
     * @return L'échelle de conversion des mètres en pixels (en pixels/mètres)
     */
    private double echelle() {
        Dimension d = this.getSize();
        // Obtient les dimensions de la simulation
        ICollisionable bordures = this.simulation.getBordures();
        Forme forme = bordures.getForme();
        Rectangle r = forme.getDimension();
        // Calcule l'échelle en p/mètre sur les 2 axes
        double echelleH = (d.getHeight() - 5) / r.getHauteur();
        double echelleV = (d.getWidth() - 5) / r.getLargeur();
        // Retourne la plus petite des deux
        return Math.min(echelleH, echelleV);
    }

    /**
     * Dessine un élément sur le JPanel
     *
     * @param e          L'élément à dessiner
     * @param graphics2D L'outils de dessin
     * @param echelle    L'échelle de conversion en pixel/mètres
     */
    private void dessineElement(IElement e, Graphics2D graphics2D, double echelle) {
        if (e instanceof IDessinable) {
            // L'element est dessinable
            ((IDessinable) e).dessine(graphics2D, echelle);
        } else {
            // L'élément n'est pas dessinable
            System.err.printf("Ne peut pas dessiner '%s'\n", e);
            // Dessine le contour de la forme en rouge
            IDessinateur<Rectangle> dessinateur = GeometrieDessinateurFactory.instance.rectangle();
            Forme forme = e.getForme();
            Rectangle contoure = forme.getDimension();
            Color precCouleur = graphics2D.getColor();
            graphics2D.setColor(Color.RED);
            dessinateur.dessine(graphics2D, echelle, false, contoure);
            graphics2D.setColor(precCouleur);
        }
    }

    @Override
    public String toString() {
        return "SimulationPanel{" + "simulation=" + this.simulation + "} " + super.toString();
    }
}
