package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

//@QuarkusMain
public class Main1 {

    public static void main(String[] args) {
        Quarkus.run(MyMain1.class, args);
    }

    public static class MyMain1 implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Indeed in Main1");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
