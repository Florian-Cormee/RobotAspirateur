package fr.rob4.simulation.vue.element;

import fr.rob4.simulation.element.Obstacle;
import fr.rob4.simulation.geometrie.Forme;
import fr.rob4.simulation.vue.IDessinable;
import fr.rob4.simulation.vue.IDessinateur;
import fr.rob4.simulation.vue.forme.GeometrieDessinateurFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Objects;

public class ObstacleDessinable extends Obstacle implements IDessinable {
	private Color couleur;

	/**
	 * Crée un obstacle à partir de sa forme.
	 *
	 * @param forme   La forme ne peut pas être <code>null</code>
	 * @param couleur La couleur de remplissage
	 */
	public ObstacleDessinable(Forme forme, Color couleur) {
		super(forme);
		this.couleur = Objects.requireNonNull(couleur);
	}

	@Override
	public void dessine(Graphics2D graphics2D, double echelle) {
		Color precCouleur = graphics2D.getColor();
		Stroke precStroke = graphics2D.getStroke();
		graphics2D.setColor(this.couleur);
		graphics2D.setStroke(new BasicStroke(2));
		// Appelle le dessinateur pour la forme de l'obstacle
		IDessinateur<Forme> dessinateur = GeometrieDessinateurFactory.instance.forme();
		dessinateur.dessine(graphics2D, echelle, false, this.forme);

		graphics2D.setColor(precCouleur);
		graphics2D.setStroke(precStroke);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.couleur);
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
		ObstacleDessinable that = (ObstacleDessinable) o;
		return Objects.equals(this.couleur, that.couleur);
	}

	@Override
	public String toString() {
		return "ObstacleDessinable[" + "couleur=" + couleur + ", super.toString()=" + super.toString() + ']';
	}
}
