package mcs.salazar.jesus.reactivejavademo;

import org.junit.Test;

import io.reactivex.Observable;

import static junit.framework.Assert.assertTrue;

/**
 * Created by jesussalazar on 5/3/18.
 */

public class RxJavaUnitTest {
    String result="";

    // Simple subscription to a fix value
    @Test
    public void returnAValue(){
        result = "";
        Observable<String> observer = Observable.just("Hello"); // provides data
        observer.subscribe(s -> result=s); // Callable as subscriber
        assertTrue(result.equals("Hello"));
    }
}