package org.acme;

import io.quarkus.arc.Arc;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class TestBean {

    public ReentrantLock lock = new ReentrantLock();

    @Bulkhead(2)
    public String get() throws InterruptedException {
        synchronized (this) {
            lock.lock();
            try {
//            new RuntimeException().printStackTrace();
                return Arc.container().select(GreetingResource.class).get().toString() + invalidate();
            } finally {
                lock.unlock();
            }
        }
    }

    public String invalidate() {
        return "invalidate";
    }
}
