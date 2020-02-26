package fr.rob4.simulation.vue;

import fr.rob4.simulation.Simulation;

import javax.swing.JPanel;
import java.awt.Graphics2D;

public class SimulationDessinateur extends JPanel implements IDessinateur<Simulation> {
    @Override
    public void dessine(Graphics2D graphics2D, double echelle, Simulation element) {

    }

    @Override
    public Class<Simulation> getType() {
        return Simulation.class;
    }
}
