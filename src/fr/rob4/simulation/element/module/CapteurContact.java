package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.ICollisionable;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

import java.util.List;
import java.util.Objects;

public class CapteurContact extends Element implements IModule<Boolean> {
    protected ICollisionable element;

    /**
     * Crée un capteur de contact à partir de la forme de sa zone d'activation
     *
     * @param forme La forme de sa zone d'activation
     */
    public CapteurContact(Forme forme) {
        super(forme);
        this.element = null;
    }

    /**
     * Actualise le capteur de contacts
     *
     * @param simulation La simulation qui demandant l'actualisation
     * @param appeleur   L'Objet demandant la mise à jour (cet objet est alors ignoré dans la recherche de collision)
     */
    @Override
    public void actualise(Simulation simulation, Object appeleur) {
        List<ICollisionable> elements = simulation.getElements(ICollisionable.class);
        // Cherche parmis les éléments collisionable un avec lequel l'instance
        // se superpose
        this.element = null;
        for (ICollisionable element : elements) {
            if (element.equals(appeleur)) {
                continue; // Ne collisionne pas avec la source
            }
            try {
                Forme forme = element.getForme();
                if (this.forme.collisionne(forme)) {
                    this.element = element;
                }
            } catch (NoIntersectionException ignored) {
            }
            if (this.element != null) {
                break;
            }
        }
    }

    @Override
    public Boolean getInfo() {
        return this.element != null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.element);
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
        CapteurContact that = (CapteurContact) o;
        return Objects.equals(this.element, that.element);
    }

    @Override
    public String toString() {
        return "CapteurContact{" + "element=" + this.element + ", super.toString=" + super.toString() + ']';
    }
}
