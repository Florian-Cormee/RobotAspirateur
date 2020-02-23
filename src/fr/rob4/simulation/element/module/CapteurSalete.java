package fr.rob4.simulation.element.module;

import fr.rob4.simulation.Simulation;
import fr.rob4.simulation.element.Element;
import fr.rob4.simulation.element.INettoyable;
import fr.rob4.simulation.geometrie.Forme;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapteurSalete extends Element implements IModule<Boolean> {
    protected Set<INettoyable> nettoyables;

    /**
     * Crée un CapteurSalete à partir de sa zone de détection
     * @param forme La zone de détection
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
            if (forme.estSupperposee(nettoyable.getForme())) {
                this.nettoyables.add(nettoyable);
            }
        }
    }

    @Override
    public Boolean getInfo() {
        return nettoyables.size() > 0;
    }

    /**
     * Obtient l'ensemble des nettoyables qui sont détectées
     *
     * @return L'ensemble des nettoyables qui sont détectées
     */
    public Set<INettoyable> getNettoyables() {
        return nettoyables;
    }

}
