package org.acme;

import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import io.quarkus.logging.Log;

public class TestEntityCommandListener implements CommandListener {

    @Override
    public void commandStarted(CommandStartedEvent event) {
        Log.info("CommandStartedEvent " + event.getCommand());
    }

    @Override
    public void commandSucceeded(CommandSucceededEvent event) {
        Log.info("CommandSucceededEvent " + event.getCommandName());
    }

    @Override
    public void commandFailed(CommandFailedEvent event) {
        Log.info("CommandFailedEvent " + event.getCommandName());
    }
}
