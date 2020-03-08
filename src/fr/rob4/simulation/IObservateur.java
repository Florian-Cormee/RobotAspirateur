package fr.rob4.simulation;

public interface IObservateur<T> {
    /**
     * Notifie l'instance qu'un changement a eu lieu
     *
     * @param source L'élément notifiant
     */
    void notifie(IObservable<T> source);
}
