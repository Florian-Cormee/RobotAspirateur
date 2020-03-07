package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.exception.NoIntersectionException;
import fr.rob4.simulation.geometrie.Forme;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapteurSalete extends Element implements IModule<Boolean> {
    protected Set<INettoyable> nettoyables;

    /**
     * Crée un CapteurSalete à partir de sa zone de détection
     *
     * @param forme La zone de détection
     */
    public CapteurSalete(Forme forme) {
        super(forme);
        this.nettoyables = new HashSet<>();
    }

    @Override
    public void actualise(Simulation simulation, Object appeleur) {
        List<INettoyable> nettoyables = simulation.getElements(INettoyable.class);
        this.nettoyables.clear();
        for (INettoyable nettoyable : nettoyables) {
            if (nettoyable.equals(appeleur)) {
                continue;
            }
            try {
                Forme forme = nettoyable.getForme();
                if (this.forme.collisionne(forme)) {
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
        return this.nettoyables.size() > 0;
    }


    /**
     * Obtient l'ensemble des nettoyables qui sont détectées
     *
     * @return L'ensemble des nettoyables qui sont détectées
     */
    public Set<INettoyable> getNettoyables() {
        return Collections.unmodifiableSet(this.nettoyables);
    }

}
