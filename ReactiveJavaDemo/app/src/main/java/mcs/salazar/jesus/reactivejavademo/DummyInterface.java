package mcs.salazar.jesus.reactivejavademo;

/**
 * Created by jesussalazar on 5/4/18.
 */

public interface DummyInterface {
    void method1(String str);

    default void log(String str){
        System.out.println("I1 logging::"+str);
    }

    static boolean isNull(String str) {
        System.out.println("Interface Null Check");

        return str == null ? true : "".equals(str) ? true : false;
    }
}
