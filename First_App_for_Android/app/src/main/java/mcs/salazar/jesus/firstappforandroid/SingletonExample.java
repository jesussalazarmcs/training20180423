package mcs.salazar.jesus.firstappforandroid;

/**
 * Created by jesussalazar on 5/4/18.
 */

public class SingletonExample {

    String password;

    private static SingletonExample instance;

    private SingletonExample() {}

    public static SingletonExample getInstance() {
        if (instance == null) {
           instance = new SingletonExample();
        }
        return instance;
    }

    // public

}
