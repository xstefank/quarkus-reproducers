package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AlgorithmProducer {

    @ConfigProperty(name = "algorithm", defaultValue = "normal")
    String algorithm;

    @Produces
    public Algorithm produceAlgorithm() {
        switch (algorithm) {
            case "normal":
                return new NormalAlgorithm();
            case "priority":
                return new PrirorityAlgorithm();
        }
        throw new IllegalArgumentException("Unknown algorithm: " + algorithm);}
}
