package fr.rob4.simulation;

public interface IObservable<T> {
    /**
     * Ajoute un observateur.
     *
     * @param obs L'observateur à ajouter
     */
    void ajouteObservateur(IObservateur<T> obs);

    /**
     * Test que l'observateur observe l'instance
     *
     * @param obs L'observateur à tester
     *
     * @return <code>true</code> s'il observe sinon <code>false</code>
     */
    boolean estObserve(IObservateur<T> obs);

    /**
     * Retire l'observateur.
     *
     * @param obs L'observateur à retirer
     */
    void retireObservateur(IObservateur<T> obs);

    /**
     * Notifie tous les observateurs
     */
    void notifierTous();
}
