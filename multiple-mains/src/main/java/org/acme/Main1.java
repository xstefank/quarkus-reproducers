package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

//@QuarkusMain
public class Main1 {

    public static void main(String[] args) {
        System.out.println("Main1");
        Quarkus.run(args);
    }
}
