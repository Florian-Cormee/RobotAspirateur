package fr.rob4.simulation.vue;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.IElement;
import fr.rob4.simulation.geometrie.Rectangle;

@SuppressWarnings("serial")
public class SimulationDessinateur extends JPanel {
    private Simulation simulation;

    public SimulationDessinateur(Simulation simulation) {
	this.simulation = Objects.requireNonNull(simulation);
    }

    @Override
    public void paint(Graphics g) {
	super.paint(g);
	Graphics2D graphics2D = (Graphics2D) g;
	List<IElement> elements = this.simulation.getElements();
	double echelle = this.echelle();
	for (IElement e : elements) {
	    try {
		IDessinateur<IElement> dessinateur = DessinateurFactory.getInstance().createDessinateur(IElement.class);
		dessinateur.dessine(graphics2D, echelle, e);
	    } catch (UnsupportedOperationException ex) {
		System.err.printf("N'a pas pu dessiner : %s à cause de : %s\n", e, ex);
	    }
	}
    }

    private double echelle() {
	Dimension d = getSize();
	return 1;
    }
}
