package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

//@QuarkusMain
public class Main2 {

    public static void main(String[] args) {
        System.setProperty("quarkus.http.port", "8081");
        Quarkus.run(MyMain2.class, args);
    }

    public static class MyMain2 implements QuarkusApplication {

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Indeed in Main2");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
