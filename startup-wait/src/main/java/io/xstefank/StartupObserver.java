package io.xstefank;

import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.vertx.http.HttpServerStart;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Singleton;

@Singleton
@Startup
public class StartupObserver {

    private final WorkerExecutor workerExecutor;

    StartupObserver(Vertx vertx) {
        workerExecutor = vertx.createSharedWorkerExecutor("startup-executor");
    }

    public void onStartup(@ObservesAsync HttpServerStart event) {
        System.out.println("StartupObserver.onStartup");
//        workerExecutor.executeBlocking(promise -> {
            System.out.println("in promise");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("promise complete");
//            promise.complete();
//        });
//        System.out.println("StartupObserver.onStartup finished");
    }
}
