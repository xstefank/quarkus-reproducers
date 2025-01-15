package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

public class AppScopedBean {

    private static final AppScopedBean INSTANCE = new AppScopedBean();

    public static AppScopedBean getInstance() {
        return INSTANCE;
    }

    public static final class AppScopedBeanProducer {

        @Produces
        @ApplicationScoped
        public AppScopedBean appScopedBean() {
            return INSTANCE;
        }
    }
}
