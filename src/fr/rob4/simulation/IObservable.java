package fr.rob4.simulation;

public interface IObservable<T> {
    void ajouteObservateur(IObservateur<IObservable<T>> obs);

    boolean estObserve(IObservateur<IObservable<T>> obs);

    void retireObservateur(IObservateur<IObservable<T>> obs);
}
