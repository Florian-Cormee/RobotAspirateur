package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapteurSalete extends Element implements IModule<Boolean> {
    protected Set<INettoyable> nettoyables;

    /**
     * Cr�e un CapteurSalete � partir de sa zone de d�tection
     * @param forme La zone de d�tection
     */
    public CapteurSalete(Forme forme) {
        super(forme);
        nettoyables = new HashSet<>();
    }

    @Override
    public void actualise(Simulation simulation) {
        List<INettoyable> nettoyables = simulation.getElements(INettoyable.class);
        this.nettoyables.clear();
        for (INettoyable nettoyable : nettoyables) {
            try {
		if (forme.estSuperposee(nettoyable.getForme())) {
		    this.nettoyables.add(nettoyable);
		}
	    } catch (NoIntersectionException e) {
		// On ne sait pas déterminer s'il y a superposition, on passe au suivant
		e.printStackTrace();
	    }
        }
    }

    @Override
    public Boolean getInfo() {
        return nettoyables.size() > 0;
    }

    /**
     * Obtient l'ensemble des nettoyables qui sont d�tect�es
     *
     * @return L'ensemble des nettoyables qui sont d�tect�es
     */
    public Set<INettoyable> getNettoyables() {
        return nettoyables;
    }

}
