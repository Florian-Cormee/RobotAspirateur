package fr.rob4.simulation.vue;

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
public class SimulationPanel extends JPanel {
    private Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        this.simulation = Objects.requireNonNull(simulation);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        // Déplace le centre du repère du coin supérieur gauche au centre du JPanel
        Dimension dimension = this.getSize();
        double largeur = dimension.getWidth();
        double hauteur = dimension.getHeight();
        graphics2D.translate((int) (largeur / 2), (int) (hauteur / 2));
        // Inverse le sens de l'axe des ordonnées
        graphics2D.scale(1,-1);
        // Dessine chaque element
        List<IElement> elements = this.simulation.getElements();
        double echelle = this.echelle();
        for (IElement e : elements) {
            System.out.printf("Dessine '%s'\n", e);
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
        g.dispose();
    }

    private double echelle() {
        Dimension d = getSize();
        // Obtient les dimensions de la simulation
        ICollisionable bordures = simulation.getBordures();
        Forme forme = bordures.getForme();
        Rectangle r = forme.getDimension();
        // Calcule l'échelle en p/mètre sur les 2 axes
        double echelleH = d.getHeight() / r.getHauteur();
        double echelleV = d.getWidth() / r.getLargeur();
        // Retourne la plus petite des deux
        return Math.min(echelleH, echelleV);
    }
}
