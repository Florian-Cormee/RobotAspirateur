package fr.rob4.simulation.vue;

import fr.rob4.simulation.geometrie.Cercle;

import java.util.HashMap;
import java.util.Map;

public class DessinateurFactory {
    private static DessinateurFactory instance;
    private Map<Class<?>, IDessinateur<?>> dessinateurMap;

    private DessinateurFactory() {
        dessinateurMap = new HashMap<>();
    }

    /**
     * Obtient l'instance de DessinateurFactory
     *
     * @return L'instance de DessinateurFactory
     */
    public static DessinateurFactory getInstance() {
        if (instance == null) {
            instance = new DessinateurFactory();
        }
        return instance;
    }

    /**
     * Crée une instance de dessinateur pour le type demandé
     * <p>
     * Garde les types précédemment demandés. Si un dessinateur du même type existe il est retourné. S'il n'en
     * existe pas encore, une instance est crée et retournée.
     * </p>
     *
     * @param c   La classe du type dessiné
     * @param <T> Le type dessiné
     *
     * @return Un dessinateur pour le type demandé
     *
     * @throws UnsupportedOperationException Quand il n'existe pas d'implémentation de dessinateur pour le type demandé
     */
    @SuppressWarnings("unchecked")
    public <T> IDessinateur<T> createDessinateur(Class<T> c) {
        if (dessinateurMap.containsKey(c)) {
            return (IDessinateur<T>) dessinateurMap.get(c);
        }
        if (c == Cercle.class) {
            IDessinateur<Cercle> dessinateur = new CercleDessinateur();
            dessinateurMap.put(dessinateur.getType(), dessinateur);
            return (IDessinateur<T>) dessinateur;
        }
        throw new UnsupportedOperationException(String.format("Pas de dessinateur pour le type: %s", c.getName()));
    }
}
