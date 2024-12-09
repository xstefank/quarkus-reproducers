package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

//@QuarkusMain
public class Main2 {

    public static void main(String[] args) {
        System.out.println("Main2");
        System.setProperty("quarkus.http.port", "8081");
        Quarkus.run(args);
    }
}
